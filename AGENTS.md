# FluentAssert - Agent Instructions

FluentAssert is a Kotlin library of `.assert()` extension functions that delegate to AssertJ. Published
artifact: `me.ahoo.test:fluent-assert-core`. There is no application or runtime entry point.

Read these before changing things; they are the single source for their topics and are not repeated here:

- `CONTRIBUTING.md`: quality gates and commands, project layout, the checklist for adding an overload, test
  kinds, the documentation map, commit/PR conventions, branching, review, versioning and releases.
- `README.md`: the type-to-assertion table and the overload resolution rules users see.
- `REQUIREMENTS.md`: the guarantees the library makes and what verifies them.

## Commands

```bash
./gradlew build                                         # Everything CI runs: tests, integration tests, detekt, ABI, coverage, headers
./gradlew :core:test --tests "*JdkTest*"                # One test class pattern
./gradlew :core:test --tests "me.ahoo.test.asserts.docs.*"   # Documentation examples and consistency
./gradlew :core:updateKotlinAbi                         # After an intended public API change
./gradlew detekt --auto-correct                         # Fix formatting
./gradlew kotlinConsumerRepository && ./gradlew -p compatibility/kotlin-consumer test   # Kotlin 2.1 consumer check
```

JDK 17 is required. Before claiming a library change is complete, run at least `./gradlew :core:test`; for
broad changes run `./gradlew build`.

## Architecture

Keep the thin delegation model unless the user explicitly asks for a design change:

```kotlin
fun Boolean?.assert(): BooleanAssert = BooleanAssert(this)
```

- One file per JDK area under `core/src/main/kotlin/me/ahoo/test/asserts/` (`Jdk.kt`, `JdkTime.kt`, `JdkIO.kt`,
  `JdkConcurrent.kt`, `JdkFunction.kt`, `Throwable.kt`, `AssertProvider.kt`). Only `:core` contains library API.
- Every receiver is nullable except `AssertProvider<A>`. Do not remove nullable receiver support.
- Prefer public AssertJ constructors. `JdkTime.kt` casts `assertThat(...)` results where constructors are
  protected (its file header explains why); `ScheduledFuture` upcasts to `Future` because `assertThat` is
  ambiguous for it.
- Add no assertion semantics of your own; the value is the Kotlin API over AssertJ.

## Overload resolution

Kotlin resolves by the static receiver type, so adding or widening an overload changes resolution for existing
call sites: treat it as a behavior change (minor version) and pin it in `OverloadResolutionContractTest`.
Verified facts that drive design decisions:

- The `Comparable` overload is as specific as the interface overloads. A type that is `Comparable` and also
  `Iterable`/`Map`/`Future`/`Throwable` is ambiguous; JDK types of this shape need a dedicated overload (as
  `ScheduledFuture`, `StringBuilder` and `StringBuffer` have), user types must use a statically typed receiver.
- There is deliberately no `CharSequence` overload: it would make `CharBuffer` and similar types ambiguous.
  `OverloadResolutionContractTest` pins `CharBuffer` to keep compiling.
- Intuition about Kotlin overload resolution is unreliable here. Verify every resolution claim with a test
  before writing it down, and reproduce suspected resolution bugs with a failing test first.

## Documentation rules

- Examples are code. KDoc examples are `@sample` functions in `core/src/test/.../samples/`; README and skill
  Kotlin blocks must exist verbatim in `docs/ReadmeExamples.kt` / `docs/SkillExamples.kt`. `DocumentationTest`
  enforces this, the type tables, and install versions.
- An example must exercise the overload it documents (a `List` receiver never demonstrates `IterableAssert`).
- Keep `README.zh-CN.md` in sync with `README.md`.
- When changing `skills/fluent-assert/`, keep `FULL-API.md` and `evals/evals.json` consistent with the sources
  and add a regression eval for new guidance.
- Record user-visible changes under `Unreleased` in `CHANGELOG.md`.

## Git and pull requests

- PR titles use `[category] : summary`; do not add a `[codex]` prefix.
- Keep commits scoped; do not mix source changes, regenerated documentation and tooling cleanup unless asked.
- Leave unrelated untracked or modified files alone.

## Boundaries

- Always: preserve the thin delegation model, add focused tests for new extensions, run the relevant Gradle
  verification before claiming completion.
- Ask first: changing public extension signatures, publication settings or Maven coordinates, adding
  dependencies, modifying release workflows, raising the consumer Kotlin level (`languageVersion`/`apiVersion`
  and `KOTLIN_CONSUMER_STDLIB_VERSION` in `build.gradle.kts`, the minimum in
  `compatibility/kotlin-consumer/settings.gradle.kts`).
- Never: commit secrets or local machine files, bypass signing or publishing safeguards, rewrite build
  infrastructure for an unrelated feature.
