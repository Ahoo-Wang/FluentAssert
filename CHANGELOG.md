# Changelog

All notable changes to this project are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and the project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html) as defined in
[CONTRIBUTING.md](CONTRIBUTING.md#versioning-and-releases).

## [Unreleased]

## [1.3.0] - 2026-11-07

Binary compatible with 1.2.x. Recompiled code can see one behavior change: `Array` and `Stream` receivers are
covariant, so `vararg` parameters and projected arrays and streams now support content checks. The jar can be
used on the JPMS module path, and `assertThrownBy` reports through AssertJ's configured representation.

### Added
- CI checks consumer compatibility: code using FluentAssert is compiled and tested with the minimum supported
  Kotlin (2.1.21) and the build toolchain's Kotlin, resolving the library from its generated POMs.
- The jar declares `Automatic-Module-Name: me.ahoo.test.asserts`, so it loads on the JPMS module path; the name
  derived from the file name (`fluent.assert.core`) was invalid. ([#146])

### Changed
- `Array` and `Stream` receivers are covariant (`Array<out T>`, `Stream<out T>`): `vararg` parameters and projected
  arrays and streams assert on their element type instead of failing to compile on content checks such as
  `contains`. Existing call sites keep their return types; the JVM signatures are unchanged. ([#139])

### Fixed
- `assertThrownBy` no longer renders a matching throwable or emits its own description on success, so a throwable
  whose `toString()` throws passes, and AssertJ's description consumer only sees user descriptions. A wrong-type
  failure renders the throwable with the configured `Representation` (`Assertions.useRepresentation`). ([#137])
- `Stream.assert()` documentation: the first content check consumes the stream, not creating the assertion. ([#138])
- The reason for having no `CharSequence` overload is documented correctly (it would change existing return types,
  not cause an ambiguity), and nullable `CharSequence?` text is converted with `?.toString()` in the READMEs and the
  skill. ([#142], [#144])
- Build: `./gradlew build --configuration-cache` succeeds (`checkLicenseHeaders` captured the build script). ([#141])
- Build: the Kotlin consumer build accepts `-Pversion` and `-PfluentAssertRepository`, which
  `kotlinConsumerRepository` prints, so it resolves the artifacts of a customized build directory or version. ([#143])
- Release: `Packages Deploy` runs when a draft release is published. ([#140])

## [1.2.1] - 2026-10-11

No API change; the library behaves exactly like 1.2.0. This release ships the corrected documentation
(including the API docs on javadoc.io) and the new quality gates.

### Added
- `integrationTest` suite covering real file system, executor and AssertJ failure-reporting scenarios.
- CI workflow running the full `./gradlew build`, CodeQL analysis and path-based PR labeling.
- Build gates: 100% line coverage (Kover) and Apache-2.0 license headers.
- `REQUIREMENTS.md`, `SECURITY.md`, issue forms, PR template and CODEOWNERS.
- Executable documentation: KDoc examples are `@sample` tests rendered by Dokka; `DocumentationTest` compiles
  README and skill examples and checks type tables and install versions.

### Changed
- README rewritten (English and Chinese): one complete type table, overload resolution rules and links
  replace the per-overload reference, which lives in the API documentation.
- CONTRIBUTING documents which document owns which fact; AGENTS.md defers to it instead of repeating it.

### Fixed
- Documentation examples that did not compile, failed when run, or exercised a different overload than
  documented (README and KDoc); the README now states the actual Kotlin 2.1+ requirement instead of 1.8+.

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

[Unreleased]: https://github.com/Ahoo-Wang/FluentAssert/compare/v1.3.0...HEAD
[1.3.0]: https://github.com/Ahoo-Wang/FluentAssert/compare/v1.2.1...v1.3.0
[1.2.1]: https://github.com/Ahoo-Wang/FluentAssert/compare/v1.2.0...v1.2.1
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
[#137]: https://github.com/Ahoo-Wang/FluentAssert/issues/137
[#138]: https://github.com/Ahoo-Wang/FluentAssert/issues/138
[#139]: https://github.com/Ahoo-Wang/FluentAssert/issues/139
[#140]: https://github.com/Ahoo-Wang/FluentAssert/issues/140
[#141]: https://github.com/Ahoo-Wang/FluentAssert/issues/141
[#142]: https://github.com/Ahoo-Wang/FluentAssert/issues/142
[#143]: https://github.com/Ahoo-Wang/FluentAssert/issues/143
[#144]: https://github.com/Ahoo-Wang/FluentAssert/issues/144
[#146]: https://github.com/Ahoo-Wang/FluentAssert/issues/146
