# Contributing to FluentAssert

Thank you for your interest in contributing to FluentAssert! We welcome contributions from the community. This document provides guidelines and information for contributors.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Project Structure](#project-structure)
- [Making Changes](#making-changes)
- [Testing](#testing)
- [Code Style](#code-style)
- [Submitting Changes](#submitting-changes)
- [Branching Model](#branching-model)
- [Code Review](#code-review)
- [Versioning and Releases](#versioning-and-releases)
- [Reporting Issues](#reporting-issues)

## Code of Conduct

This project follows a code of conduct to ensure a welcoming environment for all contributors. By participating, you agree to:

- Be respectful and inclusive
- Focus on constructive feedback
- Accept responsibility for mistakes
- Show empathy towards other contributors
- Help create a positive community

## Getting Started

### Prerequisites

- **Java 17 or higher** - Required for building and running the project
- **Gradle** - Use the included wrapper (`./gradlew`); no separate installation needed
- **Git** - Version control

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/FluentAssert.git
   cd FluentAssert
   ```
3. Add the upstream remote:
   ```bash
   git remote add upstream https://github.com/Ahoo-Wang/FluentAssert.git
   ```

## Development Setup

### Building the Project

```bash
# Build all modules
./gradlew build

# Build specific module
./gradlew :core:build

# Clean build
./gradlew clean build
```

### Running Tests

```bash
# Run all unit tests
./gradlew test

# Run integration tests (real file system, executors, AssertJ failure reporting)
./gradlew :core:integrationTest

# Run tests for specific module
./gradlew :core:test

# Run specific test class
./gradlew test --tests "*JdkTest*"

# Run specific test method
./gradlew test --tests "*JdkTest*" --tests "*booleanTest*"

# Verify the public ABI against core/api/core.api (also part of ./gradlew check)
./gradlew :core:checkKotlinAbi

# Update the ABI dump after an intentional public API change
./gradlew :core:updateKotlinAbi
```

### Code Quality Checks

```bash
# Run static analysis (Detekt)
./gradlew detekt

# Auto-fix code style issues
./gradlew detekt --auto-correct

# Generate test coverage report
./gradlew code-coverage-report:koverHtmlReport

# Verify the 100% line coverage gate (part of ./gradlew check)
./gradlew :core:koverVerify

# Verify Apache-2.0 license headers (part of ./gradlew check)
./gradlew checkLicenseHeaders
```

`./gradlew build` runs all of the above gates; CI runs the same command on every pull request.

### IDE Setup

#### IntelliJ IDEA
1. Import the project as a Gradle project
2. Ensure Kotlin plugin is installed and updated
3. Set JDK 17 as the project SDK
4. Enable annotation processing if needed

#### VS Code
1. Install Kotlin extension
2. Install Gradle extension
3. Use JDK 17

## Project Structure

```
FluentAssert/
├── bom/                    # Bill of Materials
├── core/                   # Main library module
│   ├── src/
│   │   ├── main/kotlin/    # Source code
│   │   │   └── me/ahoo/test/asserts/
│   │   │       ├── Jdk.kt              # Core JDK type assertions
│   │   │       ├── JdkConcurrent.kt    # Concurrent programming assertions
│   │   │       ├── JdkFunction.kt      # Functional programming assertions
│   │   │       ├── JdkIO.kt            # I/O assertions
│   │   │       ├── JdkTime.kt          # Time/date assertions
│   │   │       └── Throwable.kt        # Exception testing
│   │   ├── test/kotlin/            # Unit tests and OverloadResolutionContractTest
│   │   └── integrationTest/kotlin/ # Integration tests against real JDK resources
│   ├── api/core.api        # Reviewed public ABI dump
│   └── build.gradle.kts    # Module build configuration
├── dependencies/           # Dependency platform (AssertJ BOM)
├── code-coverage-report/   # Aggregate Kover report
├── skills/                 # fluent-assert AI skill (SKILL.md, FULL-API.md, evals)
├── config/
│   └── detekt/            # Static analysis rules
├── gradle/wrapper/         # Gradle wrapper
└── build.gradle.kts       # Root build configuration
```

## Making Changes

### Types of Contributions

- **Bug fixes** - Fix existing issues
- **Features** - Add new functionality
- **Documentation** - Improve docs, examples, or comments
- **Tests** - Add or improve test coverage
- **Performance** - Optimize existing code

### Development Workflow

1. **Choose an issue** or **create a new one** describing the change
2. **Create a feature branch** from `main`:
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b fix/issue-number-description
   ```
3. **Make your changes** following the guidelines below
4. **Test thoroughly** - ensure all tests pass
5. **Update documentation** if needed
6. **Commit your changes** with clear messages

### Adding New Assertion Types

When adding support for new JDK types:

1. Create extension functions in the appropriate module file
2. Follow the naming pattern: `Type?.assert(): AssertJTypeAssert`
3. Handle nullable types appropriately
4. Pin the resolution in `OverloadResolutionContractTest` and add focused tests
5. Run `./gradlew :core:updateKotlinAbi` and commit the updated `core/api/core.api`
6. Check that no existing receiver becomes ambiguous (types that are also `Comparable` are the usual trap)
7. Update documentation, `REQUIREMENTS.md` if a guarantee changes, and `CHANGELOG.md`

Example:
```kotlin
// In JdkNewType.kt
fun NewType?.assert(): NewTypeAssert {
    return NewTypeAssert(this)
}
```

## Testing

### Test Structure

- **Unit tests** in `src/test/kotlin/`
- **Contract tests**: `OverloadResolutionContractTest` pins every receiver's overload and static return type at compile time
- **Integration tests** in `src/integrationTest/kotlin/` exercise real JDK resources and AssertJ failure messages
- **Test naming**: Use backticks for descriptive names
- **Test coverage**: 100% line coverage of production code is enforced by `koverVerify`
- **Test isolation**: Each test should be independent

### Writing Tests

```kotlin
class JdkTest {

    @Test
    fun `given Boolean when assert then BooleanAssert`() {
        val value = true
        value.assert().assert().isInstanceOf(BooleanAssert::class.java)
    }

    @Test
    fun `given nullable Boolean when assert then BooleanAssert`() {
        val value: Boolean? = null
        value.assert().assert().isInstanceOf(BooleanAssert::class.java)
    }
}
```

### Test Categories

- **Basic functionality** - Test that assertions work
- **Null safety** - Test nullable type handling
- **Edge cases** - Test boundary conditions
- **Integration** - Test with real AssertJ methods

## Code Style

### Kotlin Conventions

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use `official` Kotlin code style
- Maximum line length: **300 characters**
- Use meaningful variable and function names

### Import Organization

Use explicit imports. Wildcard imports are only allowed for `java.util.*`, `org.hamcrest.Matchers.*`,
`org.hamcrest.MatcherAssert.*` and `org.junit.jupiter.api.Assertions.*` (enforced by detekt).
Every Kotlin source file starts with the Apache-2.0 license header (enforced by `checkLicenseHeaders`).
Formatting basics are defined in `.editorconfig`.

### Documentation

- Use KDoc for all public APIs
- Include examples where helpful
- Document parameters, return values, and exceptions

```kotlin
/**
 * Creates a fluent assertion for Boolean values.
 *
 * Example:
 * ```kotlin
 * val isActive = true
 * isActive.assert().isTrue()
 * ```
 *
 * @receiver Boolean? The boolean value to assert on (nullable)
 * @return BooleanAssert A fluent assertion object for boolean values
 */
fun Boolean?.assert(): BooleanAssert = BooleanAssert(this)
```

## Submitting Changes

### Commit Guidelines

Use [Conventional Commits](https://www.conventionalcommits.org/): `type(scope): summary`, with types
`feat`, `fix`, `refactor`, `test`, `docs`, `build`, `ci` and `chore`. Keep commits scoped: do not mix
source changes, documentation regeneration and tooling cleanup.

Examples:
```
feat(core): add ScheduledFuture overload
fix(core): stop assertThrownBy from leaking diagnostics
build: decouple consumer Kotlin requirements from the build toolchain
```

### Pull Request Process

1. **Ensure your branch is up to date**:
   ```bash
   git fetch upstream
   git merge upstream/main
   ```

2. **Run full test suite**:
   ```bash
   ./gradlew clean build
   ```

3. **Create a Pull Request**:
   - Title format: `[category] : summary` (for example `[feature] : add Char overload`)
   - Fill in the [pull request template](.github/pull_request_template.md)
   - Reference related issues (`Fixes #123`)
   - Labels are applied automatically from the changed paths

4. **Address review feedback**:
   - Make requested changes
   - Rebase if needed
   - Keep conversation focused

## Branching Model

- `main` is the only long-lived branch and is always releasable.
- Work happens on short-lived topic branches (`feature/...`, `fix/...`, `docs/...`) created from `main`.
- Changes reach `main` only through pull requests that pass CI and review, and are **squash-merged**
  so each pull request becomes one commit on `main`.
- Releases are tags (`vX.Y.Z`) on `main`; there are no release branches.

## Code Review

- Every pull request needs an approving review from a [code owner](.github/CODEOWNERS) and a green CI
  (`Build`, `CodeQL`, `Codecov`).
- Reviewers check in particular: overload-resolution impact on existing call sites, the ABI dump diff,
  contract and integration test coverage, and documentation/skill consistency.
- Review comments are resolved by follow-up commits on the same branch; do not force-push during review.

## Versioning and Releases

FluentAssert follows [Semantic Versioning](https://semver.org/). Because Kotlin picks overloads from the
static receiver type, the public API is the overload set itself:

| Change | Version bump |
|--------|--------------|
| Remove or change a public signature (ABI dump removes a line) | **major** |
| Add an overload, even if existing call sites resolve to it when recompiled | **minor** |
| Bug fix, documentation, build or dependency change without API impact | **patch** |

Release process:

1. Move the `Unreleased` entries in [CHANGELOG.md](CHANGELOG.md) to the new version.
2. Bump `version` in `gradle.properties` and the install snippets (README en/zh, `FULL-API.md`) in a
   `[chore] : bump version to X.Y.Z` pull request.
3. After it is merged, create a GitHub Release `vX.Y.Z` on `main`. The `Packages Deploy` workflow signs
   and publishes to GitHub Packages and Maven Central.

## Reporting Issues

### Bug Reports

When reporting bugs, please include:

- **Clear title** describing the issue
- **Steps to reproduce** the problem
- **Expected behavior** vs actual behavior
- **Environment details** (OS, Java version, etc.)
- **Code samples** if applicable
- **Stack traces** for exceptions

### Feature Requests

For new features, please provide:

- **Clear description** of the proposed feature
- **Use case** - why is this needed?
- **Example usage** if possible
- **Alternative solutions** considered

## Recognition

Contributors will be recognized in:
- GitHub repository contributors list
- Release notes for significant contributions
- Project documentation

Thank you for contributing to FluentAssert! 🎉