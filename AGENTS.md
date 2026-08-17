# FluentAssert - Agent Instructions

## Overview

FluentAssert is a Kotlin library that exposes fluent `.assert()` extension functions for common JDK types. The implementation is intentionally thin: extension functions delegate to AssertJ assertion classes and keep Kotlin call sites idiomatic.

Published artifact: `me.ahoo.test:fluent-assert-core`.

## Build & Run

```bash
./gradlew build                                  # Build all modules
./gradlew :core:build                            # Build the core library only
./gradlew clean                                  # Remove Gradle build outputs
./gradlew detekt                                 # Run Detekt (reports only, no auto-fix)
./gradlew detekt --auto-correct                  # Run Detekt with auto-correction
./gradlew code-coverage-report:koverXmlReport    # Generate aggregate Kover XML coverage
```

There is no application server or runtime entry point. This repository is a library.

JDK 17 is required. Gradle configures the Kotlin JVM toolchain automatically.

## Testing

```bash
./gradlew test                                                              # Run all tests
./gradlew :core:test                                                        # Run core tests
./gradlew :core:test --tests "*JdkTest*"                                    # Run one test class pattern
./gradlew :core:test --tests "*JdkTest*" --tests "*given Boolean when*"     # Run one method pattern
```

Tests use JUnit 5 and MockK. CI enables Gradle test retry with up to two retries and `failOnPassedAfterRetry = true`.

Before committing changes to library behavior, run at least `./gradlew :core:test`. For broad changes, run `./gradlew build`.

For suspected API bugs (wrong overload picked, wrong AssertJ type returned), write a failing test that reproduces the exact symptom and confirm RED before touching implementation. Overload resolution is decided by the static receiver type, so adding or widening an overload changes resolution for existing call sites — treat that as a behavior change.

## Project Structure

```text
core/                         # Published library module, source and tests live here
dependencies/                 # Java platform module pinning dependency versions
bom/                          # Published BOM for consumers
code-coverage-report/         # Aggregate Kover/Jacoco reporting module, not published
skills/                       # fluent-assert skill plugin sources (SKILL.md, FULL-API.md, evals)
config/detekt/detekt.yml      # Detekt rules
.github/workflows/            # Coverage and package publishing workflows
```

Main source package: `core/src/main/kotlin/me/ahoo/test/asserts/`.

Key files:

- `Jdk.kt` covers primitives, strings, BigDecimal, collections, arrays, optionals, streams, generic objects, and comparable values.
- `JdkTime.kt` covers Date, java.time temporal values, Duration, and Period.
- `JdkIO.kt` covers Path, File, URL, and URI.
- `JdkConcurrent.kt` covers Future, CompletableFuture, and CompletionStage.
- `JdkFunction.kt` covers Predicate.
- `Throwable.kt` covers Throwable assertions and `assertThrownBy<T>`.
- `AssertProvider.kt` covers AssertJ `AssertProvider<A>`.

Only `:core` should contain library APIs. Other modules are build, dependency, BOM, or reporting infrastructure.

## Architecture

Use the existing pattern unless the user explicitly asks for a design change:

```kotlin
fun Boolean?.assert(): BooleanAssert = BooleanAssert(this)

fun <T> Iterable<T>?.assert(): IterableAssert<T> = assertThat(this)
```

All receivers are nullable (including the Comparable overload `fun <T : Comparable<T>?> T?.assert()`), so callers can assert `null` values fluently. `AssertProvider<A>.assert()` is non-nullable because AssertJ handles provider delegation.

`JdkTime.kt` uses `assertThat(...) as XxxAssert` casts because AssertJ's temporal factories return wildcard self-references (`AbstractLocalDateAssert<?>`) and most concrete temporal assert classes have no public constructor; the file header explains this. Prefer direct constructors where they exist (as `JdkIO.kt` does).

Do not add custom assertion semantics in this library unless requested. The library's value is the Kotlin extension API over AssertJ, not reimplementing AssertJ behavior.

## Overload-Resolution Gotchas

These are verified facts about how the overloads resolve; keep docs and examples consistent with them:

- Primitive arrays (`IntArray`, `LongArray`, `ByteArray`, ...) are not `Array<T>` and fall back to `T?.assert()` → `ObjectAssert`. README documents `.toList()`/`.toTypedArray()` as the workaround; extending coverage is an ask-first API change.
- A statically `CharSequence`-typed receiver resolves to `ObjectAssert`, not `StringAssert`.
- `StringBuilder` implements `Comparable<StringBuilder>`, so `sb.assert()` resolves to `GenericComparableAssert<StringBuilder>` — still no string assertions.
- `CompletionStage<V>.assert()` returns `CompletableFutureAssert<V>`; there is no `CompletionStageAssert`.
- KDoc and skill examples must genuinely exercise the overload they document — e.g. a `String` receiver never demonstrates the `GenericComparableAssert` overload because it resolves to `StringAssert`.
- When documenting resolution behavior, verify the claim with a throwaway test before writing it down; intuition about Kotlin overload resolution is unreliable here.

## Code Style

- Kotlin compiler flags are `-Xjsr305=strict` and `-Xjvm-default=all-compatibility`.
- Import the extension with `import me.ahoo.test.asserts.assert` in examples and tests.
- Keep KDoc examples short and executable-looking.
- Follow existing test naming with backtick JUnit method names, for example `fun \`given Boolean when assert then BooleanAssert\`()`.
- Tests commonly verify the returned AssertJ type with `value.assert().assert().isInstanceOf(...)`.
- Detekt max line length is 300. Wildcard imports are allowed only for `java.util.*`, `org.hamcrest.Matchers.*`, `org.hamcrest.MatcherAssert.*`, and `org.junit.jupiter.api.Assertions.*` (see `config/detekt/detekt.yml`); `java.time.*` and others fail lint.

## Git & PR Workflow

- PR titles should use `[category] : summary`.
- Do not add a `[codex]` prefix to PR titles.
- Keep commits scoped. Do not mix source changes, generated documentation, and local tooling cleanup unless the user requested the combined change.
- Leave unrelated untracked or modified files alone.

## Publishing & CI

Coverage CI runs `./gradlew code-coverage-report:koverXmlReport --stacktrace` and uploads `code-coverage-report/build/reports/kover/report.xml`.

Package publishing is triggered by GitHub Release creation or manual workflow dispatch. It publishes to GitHub Packages and Maven Central using signing and repository credentials from CI secrets.

When changing the skill under `skills/fluent-assert/`, treat `skills/fluent-assert/references/FULL-API.md` and `evals/evals.json` as part of the change: factual claims must match the `:core` sources, and new guidance should get a regression eval prompt.

## Boundaries

- Always do: preserve the thin AssertJ delegation model, add or update focused tests for new extension functions, and run the relevant Gradle verification before claiming a change is complete.
- Ask first: changing public extension signatures, changing module publication settings, adding dependencies, modifying release workflows, or changing Maven coordinates.
- Never do: commit secrets or local machine files, bypass signing/publishing safeguards, remove nullable receiver support accidentally, or rewrite build infrastructure for an unrelated feature.
