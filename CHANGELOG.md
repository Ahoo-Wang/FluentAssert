# Changelog

All notable changes to this project are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and the project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html) as defined in
[CONTRIBUTING.md](CONTRIBUTING.md#versioning-and-releases).

## [Unreleased]

### Added
- `integrationTest` suite covering real file system, executor and AssertJ failure-reporting scenarios.
- CI workflow running the full `./gradlew build`, CodeQL analysis and path-based PR labeling.
- Build gates: 100% line coverage (Kover) and Apache-2.0 license headers.
- `REQUIREMENTS.md`, `SECURITY.md`, issue forms, PR template and CODEOWNERS.

## [1.2.0] - 2026-10-11

### Added
- `Char` → `CharacterAssert`, `BigInteger` → `BigIntegerAssert`. ([#126])
- `StringBuilder` / `StringBuffer` → `CharSequenceAssert`. ([#126])
- Primitive arrays (`BooleanArray` … `CharArray`) → matching `XxxArrayAssert`. ([#126])
- `ScheduledFuture` → `FutureAssert`. ([#124])
- `OverloadResolutionContractTest` and KGP ABI validation lock the public API. ([#124])

### Changed
- Call sites on the receivers above resolve to the new, more specific assertion classes when recompiled
  (binary compatible). ([#126])
- Compiled with Kotlin language/API version 2.2 and depends on `kotlin-stdlib` 2.2.21 instead of the
  build compiler's version. ([#125])

### Fixed
- `ScheduledFuture.assert()` no longer fails with an overload-resolution ambiguity. ([#124])
- Published POM no longer leaks `mockk` / `detekt-formatting` version constraints. ([#124])
- The javadoc jar contains Dokka documentation instead of being empty. ([#124])

## [1.1.0] - 2026-08-17

### Fixed
- Nullable `Comparable` receivers resolve to `GenericComparableAssert`. ([#93])
- `assertThrownBy` no longer leaks its diagnostics into chained assertions. ([#99])

## [1.0.0] - 2026-05-27

### Changed
- First stable release; documentation and skill examples polished. ([#74])

## [0.2.0] - 2025-06-25 … [0.2.8] - 2026-05-07

### Changed
- Dependency and build tooling updates; Sonatype publishing configuration. See the
  [GitHub releases](https://github.com/Ahoo-Wang/FluentAssert/releases) for details.

## [0.1.0] - 2025-04-22

### Added
- `Throwable` assertions and `assertThrownBy`, `CompletableFuture` / `CompletionStage` and
  `Comparable` assertions. ([#7], [#8], [#9])

## [0.0.1] - 2025-04-17 … [0.0.6] - 2025-04-21

### Added
- Initial `.assert()` extensions, `Iterable` support and nullable receivers. ([#3], [#4])

[Unreleased]: https://github.com/Ahoo-Wang/FluentAssert/compare/v1.2.0...HEAD
[1.2.0]: https://github.com/Ahoo-Wang/FluentAssert/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/Ahoo-Wang/FluentAssert/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/Ahoo-Wang/FluentAssert/compare/v0.2.8...v1.0.0
[0.2.0]: https://github.com/Ahoo-Wang/FluentAssert/compare/v0.1.2...v0.2.0
[0.2.8]: https://github.com/Ahoo-Wang/FluentAssert/compare/v0.2.6...v0.2.8
[0.1.0]: https://github.com/Ahoo-Wang/FluentAssert/compare/v0.0.6...v0.1.0
[0.0.1]: https://github.com/Ahoo-Wang/FluentAssert/releases/tag/v0.0.1
[0.0.6]: https://github.com/Ahoo-Wang/FluentAssert/compare/v0.0.5...v0.0.6
[#3]: https://github.com/Ahoo-Wang/FluentAssert/pull/3
[#4]: https://github.com/Ahoo-Wang/FluentAssert/pull/4
[#7]: https://github.com/Ahoo-Wang/FluentAssert/pull/7
[#8]: https://github.com/Ahoo-Wang/FluentAssert/pull/8
[#9]: https://github.com/Ahoo-Wang/FluentAssert/pull/9
[#74]: https://github.com/Ahoo-Wang/FluentAssert/pull/74
[#93]: https://github.com/Ahoo-Wang/FluentAssert/pull/93
[#99]: https://github.com/Ahoo-Wang/FluentAssert/pull/99
[#124]: https://github.com/Ahoo-Wang/FluentAssert/pull/124
[#125]: https://github.com/Ahoo-Wang/FluentAssert/pull/125
[#126]: https://github.com/Ahoo-Wang/FluentAssert/pull/126
