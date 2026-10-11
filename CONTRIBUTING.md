# Contributing to FluentAssert

Thanks for helping improve FluentAssert. Be respectful, keep feedback constructive, and assume good intent.

- [Development setup](#development-setup)
- [Quality gates](#quality-gates)
- [Project layout](#project-layout)
- [Adding or changing an overload](#adding-or-changing-an-overload)
- [Tests](#tests)
- [Documentation](#documentation)
- [Code style](#code-style)
- [Commits and pull requests](#commits-and-pull-requests)
- [Branching model](#branching-model)
- [Code review](#code-review)
- [Versioning and releases](#versioning-and-releases)
- [Reporting issues](#reporting-issues)

## Development setup

You need JDK 17 and Git; Gradle comes with the wrapper.

```bash
git clone https://github.com/Ahoo-Wang/FluentAssert.git
cd FluentAssert
./gradlew build
```

Import the project into IntelliJ IDEA as a Gradle project with JDK 17.

## Quality gates

`./gradlew build` runs every gate below; CI runs the same command on every pull request.

| Command | Gate |
|---|---|
| `./gradlew :core:test` | Unit, contract, sample and documentation tests |
| `./gradlew :core:integrationTest` | Integration tests against real JDK resources |
| `./gradlew detekt` | Static analysis and formatting (`--auto-correct` to fix formatting) |
| `./gradlew :core:checkKotlinAbi` | Public ABI matches `core/api/core.api` (`:core:updateKotlinAbi` after an intended change) |
| `./gradlew :core:koverVerify` | 100% line coverage of production code |
| `./gradlew checkLicenseHeaders` | Apache-2.0 header in every Kotlin source |

Consumer compatibility is a separate build, run by the `Kotlin minimum consumer` and `Kotlin toolchain consumer`
CI jobs. It compiles and tests code using FluentAssert with an older Kotlin compiler, resolving the library only
from its generated POMs:

```bash
./gradlew kotlinConsumerRepository                                         # Assemble build/kotlin-consumer-repo
./gradlew -p compatibility/kotlin-consumer test                            # Minimum supported Kotlin (2.1.21)
./gradlew -p compatibility/kotlin-consumer test -PkotlinVersion=2.4.21     # Any other compiler version
```

Run a subset with `./gradlew :core:test --tests "*JdkTest*"`. The aggregate coverage report is
`./gradlew code-coverage-report:koverHtmlReport`.

## Project layout

```text
core/
├── src/main/kotlin/me/ahoo/test/asserts/   # The library: one file per JDK area (Jdk.kt, JdkTime.kt, ...)
├── src/test/kotlin/me/ahoo/test/asserts/
│   ├── *Test.kt                            # Unit tests and OverloadResolutionContractTest
│   ├── samples/                            # KDoc @sample functions, executed as tests
│   └── docs/                               # DocumentationTest and the README/skill example copies
├── src/integrationTest/kotlin/             # Integration tests
└── api/core.api                            # Reviewed public ABI dump
compatibility/kotlin-consumer/              # Standalone consumer build for Kotlin compatibility checks
dependencies/                               # Published dependency platform (AssertJ BOM)
bom/                                        # Published BOM for consumers
code-coverage-report/                       # Aggregate Kover report
skills/fluent-assert/                       # AI agent skill (SKILL.md, FULL-API.md, evals)
config/detekt/                              # Static analysis rules
```

## Adding or changing an overload

The public API *is* the overload set, and Kotlin resolves overloads by the static receiver type. Adding an
overload therefore changes which function existing call sites resolve to when recompiled.

1. Add the extension in the matching file, delegating to AssertJ with no extra behavior:
   `fun Foo?.assert(): FooAssert = FooAssert(this)` (prefer a public constructor over `assertThat(...)`).
2. Check that no existing receiver becomes ambiguous; types that are also `Comparable` are the usual trap.
3. Pin the resolution in `OverloadResolutionContractTest`.
4. Add a `@sample` function under `samples/` and reference it from a short KDoc.
5. Run `./gradlew :core:updateKotlinAbi` and commit `core/api/core.api`.
6. Add the type to the tables in both READMEs and `FULL-API.md` (`DocumentationTest` enforces this), and an
   entry under `Unreleased` in `CHANGELOG.md`.

## Tests

| Kind | Location | Purpose |
|---|---|---|
| Unit | `core/src/test/.../*Test.kt` | Behavior of each extension, named `` `given X when assert then Y` `` |
| Contract | `OverloadResolutionContractTest` | Static return type of every receiver, checked at compile time |
| Samples | `samples/` | KDoc examples; must compile and pass |
| Documentation | `docs/` | README and skill examples compiled verbatim; tables and versions in sync |
| Integration | `core/src/integrationTest/` | Real files, executors and AssertJ failure messages |

For a suspected resolution bug, first write a failing test that reproduces the exact symptom.

## Documentation

Every fact has one owner; other documents link to it instead of repeating it.

| Document | Audience | Owns |
|---|---|---|
| `README.md` / `README.zh-CN.md` | Users | What the library is, installation, the type table, resolution rules |
| KDoc + `samples/` → [API docs](https://javadoc.io/doc/me.ahoo.test/fluent-assert-core) | Users | Per-overload reference and examples |
| `CHANGELOG.md` | Users | Release history and compatibility notes |
| `REQUIREMENTS.md` | Maintainers | Guarantees and the tests that verify them |
| `CONTRIBUTING.md` | Contributors | Workflow, quality gates, policies |
| `AGENTS.md` | Coding agents | Agent-specific rules and boundaries |
| `skills/fluent-assert/` | AI assistants of library users | Self-contained usage guidance (shipped separately) |

Rules:

- Code examples are executable. KDoc uses `@sample`; Kotlin blocks using FluentAssert in the READMEs and the
  skill must also exist verbatim in `docs/ReadmeExamples.kt` or `docs/SkillExamples.kt`. Only blocks that
  need an external DSL may opt out with `<!-- not-compiled: reason -->` on the line above the fence.
- Verify resolution claims with a test before writing them down.
- `README.zh-CN.md` mirrors `README.md` section by section; change both together.

## Code style

- [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html), enforced by detekt
  (`config/detekt/detekt.yml`) and `.editorconfig`.
- Explicit imports; wildcards only for `java.util.*`, `org.hamcrest.Matchers.*`, `org.hamcrest.MatcherAssert.*`
  and `org.junit.jupiter.api.Assertions.*`.
- Every Kotlin file starts with the Apache-2.0 license header.

## Commits and pull requests

- Commits follow [Conventional Commits](https://www.conventionalcommits.org/) (`feat`, `fix`, `refactor`,
  `test`, `docs`, `build`, `ci`, `chore`) and stay scoped: do not mix source changes, regenerated docs and
  tooling cleanup.
- Pull request titles use `[category] : summary`, for example `[feature] : add Char overload`.
- Fill in the [pull request template](.github/pull_request_template.md) and link issues (`Fixes #123`).
  Labels are applied automatically from the changed paths.
- Bring your branch up to date with `git merge upstream/main`; do not force-push during review.

## Branching model

- `main` is the only long-lived branch and is always releasable; it is protected.
- Work happens on short-lived branches (`feature/...`, `fix/...`, `docs/...`) created from `main`.
- Changes reach `main` only through pull requests that pass CI and review, and are **squash-merged**.
- Releases are tags (`vX.Y.Z`) on `main`; there are no release branches.

## Code review

- Every pull request needs an approving review from a [code owner](.github/CODEOWNERS) and green required
  checks (`Build`, `Analyze (java-kotlin)`, `Codecov`, `Kotlin minimum consumer`, `Kotlin toolchain consumer`),
  with all conversations resolved.
- Reviewers focus on overload-resolution impact, the ABI dump diff, test coverage of new resolutions, and
  documentation consistency.

## Versioning and releases

FluentAssert follows [Semantic Versioning](https://semver.org/), applied to the overload set:

| Change | Bump |
|---|---|
| Remove or change a public signature (a line disappears from the ABI dump) | major |
| Add an overload, even if existing call sites resolve to it when recompiled | minor |
| Fix, documentation, build or dependency change without API impact | patch |

Release process:

1. Move the `Unreleased` entries in [CHANGELOG.md](CHANGELOG.md) to the new version.
2. In a `[chore] : bump version to X.Y.Z` pull request, update `version` in `gradle.properties` and the install
   snippets (READMEs, `FULL-API.md`; `DocumentationTest` checks they match).
3. After merging, create a GitHub Release `vX.Y.Z` on `main`. The `Packages Deploy` workflow signs and
   publishes to GitHub Packages and Maven Central.

## Reporting issues

Use the [issue forms](https://github.com/Ahoo-Wang/FluentAssert/issues/new/choose); for resolution bugs, include
the declared type of the receiver. Report vulnerabilities privately as described in [SECURITY.md](SECURITY.md).
