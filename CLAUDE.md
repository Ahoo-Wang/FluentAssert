# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FluentAssert is a Kotlin library providing fluent `.assert()` extension functions for all major JDK types, wrapping AssertJ behind idiomatic Kotlin syntax. Published as `me.ahoo.test:fluent-assert-core` on Maven Central.

## Build & Test Commands

```bash
./gradlew build                        # Build all modules
./gradlew :core:build                  # Build core library only
./gradlew test                         # Run all tests
./gradlew :core:test                   # Run core tests only
./gradlew :core:test --tests "*JdkTest*"                                      # Single test class
./gradlew :core:test --tests "*JdkTest*" --tests "*given Boolean when*"       # Single test method
./gradlew detekt                       # Lint with Detekt
./gradlew detekt --auto-correct        # Auto-fix lint issues
./gradlew code-coverage-report:koverXmlReport  # Coverage report
```

Requires JDK 17 (toolchain auto-provisions via Gradle).

**Kotlin compiler flags:** `-Xjsr305=strict` (null-safety interop with Java), `-Xjvm-default=all-compatibility` (default methods in interfaces).

## Module Structure

- **`:core`** — The library. All source and tests live here. Artifact: `fluent-assert-core`
- **`:dependencies`** — BOM pinning AssertJ, mockk, detekt-formatting versions
- **`:bom`** — Bill of Materials for consumers
- **`:code-coverage-report`** — Aggregates Kover/JaCoCo reports (not published)

Only `:core` contains library code. All other modules are build infrastructure.

**Dependency chain:** `:core` → `:dependencies` (pins AssertJ BOM, mockk, detekt-formatting versions). `:bom` constrains all library project versions for consumers.

## Architecture

All extension functions live in `core/src/main/kotlin/me/ahoo/test/asserts/`:

| File | Covers |
|---|---|
| `Jdk.kt` | Primitives, String, BigDecimal, collections (Iterable, List, Map, Array, Optional, Stream, etc.), generic `T?` and `Comparable<T>` |
| `JdkTime.kt` | Date, ZonedDateTime, LocalDateTime, OffsetDateTime, Instant, Duration, Period, etc. |
| `JdkIO.kt` | Path, File, URL, URI |
| `JdkConcurrent.kt` | Future, CompletableFuture, CompletionStage |
| `JdkFunction.kt` | Predicate |
| `Throwable.kt` | Throwable extension + `assertThrownBy<T>{}` function |
| `AssertProvider.kt` | `AssertProvider<A>` — delegates to AssertJ's `assertThat(this)` for custom assertion providers |

**Design pattern:** Most functions use `fun Type?.assert(): AssertJType` — nullable receiver for null safety, delegates to the corresponding AssertJ assertion class. `AssertProvider` uses a non-nullable receiver (`fun AssertProvider<A>.assert(): A`) since AssertJ's `assertThat` handles the delegation. No custom assertion logic.

**Import:** Always `import me.ahoo.test.asserts.assert` (not AssertJ's `assertThat`).

## Testing Conventions

- JUnit 5 (Jupiter) with backtick method names for readability
- Tests verify `.assert()` returns the correct AssertJ type via `.assert().assert().isInstanceOf(...)` pattern
- Mockk is used for mocking interface types (Iterator, Iterable, Path, etc.)
- Test retry plugin: in CI, up to 2 retries with `failOnPassedAfterRetry = true`

## Code Style

- Detekt config at `config/detekt/detekt.yml`
- Max line length: 300
- Wildcard imports allowed for `java.util.*`, `org.hamcrest.*`, `org.junit.jupiter.api.Assertions.*`
- Complexity and naming rules are relaxed (many disabled)

## Publishing

Publishes to Maven Central (Sonatype) and GitHub Packages. Release triggered by GitHub Release creation. Signing uses in-memory PGP keys in CI.
