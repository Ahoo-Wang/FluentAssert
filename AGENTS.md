# Development Guidelines for FluentAssert

## Build Commands
- **Build project**: `./gradlew build`
- **Run all tests**: `./gradlew test`
- **Run single test**: `./gradlew test --tests "*TestClass*" --tests "*testMethod*"`
- **Run tests for specific module**: `./gradlew :core:test`

## Code Quality
- **Lint code**: `./gradlew detekt`
- **Auto-fix lint issues**: `./gradlew detekt --auto-correct`
- **Check code coverage**: `./gradlew jacocoTestReport`

## Code Style Guidelines

### Language & Framework
- **Language**: Kotlin with JVM target
- **Style**: Official Kotlin coding conventions (`kotlin.code.style=official`)
- **Testing**: JUnit 5 (Jupiter) with AssertJ assertions
- **Build tool**: Gradle with Kotlin DSL

### Formatting
- **Line length**: 300 characters maximum
- **Imports**: No wildcard imports except `java.util.*`, `org.hamcrest.*`, `org.junit.jupiter.api.Assertions.*`
- **Indentation**: Follow EditorConfig settings

### Naming Conventions
- **Test methods**: Use backticks for descriptive names: `` `given X when Y then Z` ``
- **Extension functions**: Use `assert()` for fluent assertion extensions
- **Packages**: Follow `me.ahoo.test.asserts` structure

### Error Handling
- Handle nullable types appropriately in extension functions
- Use AssertJ's null-safe assertion methods
- Fail fast with descriptive error messages

### Testing Standards
- Write comprehensive tests for all public APIs
- Use descriptive test method names with backticks
- Test both nullable and non-nullable variants
- Verify correct AssertJ assertion types are returned

### Code Quality
- Every commit must compile and pass tests
- Use Detekt for static analysis
- Maintain high test coverage
- Follow single responsibility principle
- Prefer composition over inheritance