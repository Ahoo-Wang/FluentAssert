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
- **Gradle 7.0+** - Build tool (wrapper included)
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
# Run all tests
./gradlew test

# Run tests for specific module
./gradlew :core:test

# Run specific test class
./gradlew test --tests "*JdkTest*"

# Run specific test method
./gradlew test --tests "*JdkTest*" --tests "*booleanTest*"
```

### Code Quality Checks

```bash
# Run static analysis (Detekt)
./gradlew detekt

# Auto-fix code style issues
./gradlew detekt --auto-correct

# Generate test coverage report
./gradlew jacocoTestReport
```

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
│   │   └── test/kotlin/    # Test code
│   └── build.gradle.kts    # Module build configuration
├── dependencies/           # Dependency management
├── config/                 # Configuration files
│   ├── detekt/            # Static analysis rules
│   └── logback.xml        # Logging configuration
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
4. Add comprehensive tests
5. Update documentation

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
- **Test naming**: Use backticks for descriptive names
- **Test coverage**: Aim for high coverage of public APIs
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

```kotlin
// Group imports by package
import org.assertj.core.api.*
import me.ahoo.test.asserts.*

// No wildcard imports except for specified packages:
// - java.util.*
// - org.hamcrest.*
// - org.junit.jupiter.api.Assertions.*
```

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

- Use clear, descriptive commit messages
- Start with a verb in imperative mood
- Reference issue numbers when applicable

Examples:
```
Add support for Predicate assertions
Fix null handling in Date assertions
Update documentation for new features
```

### Pull Request Process

1. **Ensure your branch is up to date**:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

2. **Run full test suite**:
   ```bash
   ./gradlew clean build
   ```

3. **Create a Pull Request**:
   - Use a clear title
   - Provide detailed description
   - Reference related issues
   - Include screenshots for UI changes

4. **Address review feedback**:
   - Make requested changes
   - Rebase if needed
   - Keep conversation focused

### Pull Request Template

Please use the following template:

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Refactoring
- [ ] Performance improvement

## Testing
- [ ] All existing tests pass
- [ ] New tests added
- [ ] Manual testing performed

## Checklist
- [ ] Code follows project style guidelines
- [ ] Documentation updated
- [ ] Tests added/updated
- [ ] No breaking changes
```

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