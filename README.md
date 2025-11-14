# FluentAssert

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://github.com/Ahoo-Wang/FluentAssert/blob/main/LICENSE)
[![GitHub release](https://img.shields.io/github/release/Ahoo-Wang/FluentAssert.svg)](https://github.com/Ahoo-Wang/FluentAssert/releases)
[![Maven Central Version](https://img.shields.io/maven-central/v/me.ahoo.test/fluent-assert-core)](https://central.sonatype.com/artifact/me.ahoo.test/fluent-assert-core)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d58ca7a79749493ebb48d423e73b6e1d)](https://app.codacy.com/gh/Ahoo-Wang/FluentAssert/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![codecov](https://codecov.io/gh/Ahoo-Wang/FluentAssert/graph/badge.svg?token=iXUP7NXHSc)](https://codecov.io/gh/Ahoo-Wang/FluentAssert)
[![Integration Test Status](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/codecov.yml/badge.svg)](https://github.com/Ahoo-Wang/FluentAssert)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/Ahoo-Wang/FluentAssert)

FluentAssert is a Kotlin library that provides fluent assertions for JDK types, making your tests more readable and expressive. The library wraps AssertJ assertions with Kotlin extension functions for better syntax.

## Features

- **Fluent API**: Chain assertions in a readable, natural way
- **Null-Safe**: All extension functions handle nullable types appropriately
- **Comprehensive Coverage**: Supports all major JDK types and collections
- **Type-Safe**: Full Kotlin type system integration
- **AssertJ Powered**: Leverages the robust AssertJ assertion library
- **Zero Dependencies**: Only depends on AssertJ (transitively)
- **Kotlin Idiomatic**: Designed specifically for Kotlin developers
- **IDE Support**: Full IntelliJ IDEA and Android Studio integration

## Installation

### Requirements

- **Java**: 17 or higher
- **Kotlin**: 1.8.0 or higher
- **JUnit**: 5.x (for testing)

### Maven

```xml
<dependency>
    <groupId>me.ahoo.test</groupId>
    <artifactId>fluent-assert-core</artifactId>
    <version>0.2.2</version>
    <scope>test</scope>
</dependency>
```

### Gradle (Kotlin DSL)

```kotlin
testImplementation("me.ahoo.test:fluent-assert-core:0.2.2")
```

### Gradle (Groovy DSL)

```gradle
testImplementation 'me.ahoo.test:fluent-assert-core:0.2.2'
```

## Quick Start

Simply call `.assert()` on any JDK type to start building fluent assertions:

```kotlin
import me.ahoo.test.asserts.assert

// Basic assertions
val name = "FluentAssert"
name.assert().startsWith("Fluent").endsWith("Assert")

val age = 25
age.assert().isGreaterThan(18).isLessThan(100)

val isActive = true
isActive.assert().isTrue()
```

## API Reference

### Core Extension Functions

All extension functions follow the pattern `Type.assert(): AssertJTypeAssert`, where:

- `Type` is any supported JDK type (nullable or non-nullable)
- `AssertJTypeAssert` is the corresponding AssertJ assertion class

### Supported Types

| Category        | Types                                                                                                                                                        |
|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Primitives**  | `Boolean`, `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`, `BigDecimal`                                                                                   |
| **Text**        | `String`                                                                                                                                                     |
| **Collections** | `Iterable<T>`, `Iterator<T>`, `Collection<T>`, `Array<T>`, `List<T>`, `Map<K,V>`, `Optional<T>`, `Stream<T>`                                                 |
| **Time/Date**   | `Date`, `ZonedDateTime`, `LocalDateTime`, `OffsetDateTime`, `OffsetTime`, `LocalTime`, `LocalDate`, `YearMonth`, `Instant`, `Duration`, `Period`, `Temporal` |
| **I/O**         | `Path`, `File`, `URL`, `URI`                                                                                                                                 |
| **Concurrent**  | `Future<V>`, `CompletableFuture<V>`, `CompletionStage<V>`                                                                                                    |
| **Functional**  | `Predicate<T>`                                                                                                                                               |
| **Exceptions**  | `Throwable`                                                                                                                                                  |

### Exception Testing Functions

- `assertThrownBy<T : Throwable>(shouldRaiseThrowable: () -> Unit): ThrowableAssert<T>` - Asserts that code throws a specific exception type
- `Throwable.assert(): ThrowableAssert<Throwable>` - Creates assertions for exception instances

## Complete API Reference

### Core JDK Types

#### Primitive Types

##### `Boolean?.assert(): BooleanAssert`
Creates assertions for boolean values.

```kotlin
true.assert().isTrue()
false.assert().isFalse()
val nullableBool: Boolean? = null
nullableBool.assert().isNull()
```

##### `Byte?.assert(): ByteAssert`
Creates assertions for byte values.

```kotlin
val value: Byte = 42
value.assert().isEqualTo(42).isPositive()
```

##### `Short?.assert(): ShortAssert`
Creates assertions for short values.

```kotlin
val value: Short = 1000
value.assert().isEqualTo(1000).isGreaterThan(0)
```

##### `Int?.assert(): IntegerAssert`
Creates assertions for integer values.

```kotlin
val age = 25
age.assert().isEqualTo(25).isBetween(18, 65)
```

##### `Long?.assert(): LongAssert`
Creates assertions for long values.

```kotlin
val timestamp = System.currentTimeMillis()
timestamp.assert().isPositive().isGreaterThan(0)
```

##### `Float?.assert(): FloatAssert`
Creates assertions for float values.

```kotlin
val pi = 3.14f
pi.assert().isEqualTo(3.14f).isPositive()
```

##### `Double?.assert(): DoubleAssert`
Creates assertions for double values.

```kotlin
val price = 19.99
price.assert().isEqualTo(19.99).isPositive()
```

##### `BigDecimal?.assert(): BigDecimalAssert`
Creates assertions for BigDecimal values.

```kotlin
val amount = BigDecimal("123.45")
amount.assert().isEqualTo("123.45").isPositive()
```

#### Text Types

##### `String?.assert(): StringAssert`
Creates assertions for string values.

```kotlin
val name = "FluentAssert"
name.assert()
    .startsWith("Fluent")
    .endsWith("Assert")
    .contains("uentAss")
    .hasLength(11)
```

#### Generic Types

##### `<T> T?.assert(): ObjectAssert<T>`
Creates assertions for any object type.

```kotlin
val person = Person("John", 30)
person.assert()
    .isNotNull()
    .hasFieldOrPropertyWithValue("name", "John")
```

##### `<T : Comparable<T>?> T.assert(): GenericComparableAssert<T>`
Creates assertions for comparable objects.

```kotlin
val version = "2.0.0"
version.assert()
    .isGreaterThan("1.0.0")
    .isLessThan("3.0.0")
```

### Collections

##### `<T> Iterable<T>?.assert(): IterableAssert<T>`
Creates assertions for iterable collections.

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
numbers.assert()
    .hasSize(5)
    .contains(3)
    .doesNotContain(6)
    .allMatch { it > 0 }
```

##### `<T> Iterator<T>?.assert(): IteratorAssert<T>`
Creates assertions for iterators.

```kotlin
val iterator = listOf(1, 2, 3).iterator()
iterator.assert().hasNext()
```

##### `<T> Collection<T>?.assert(): CollectionAssert<T>`
Creates assertions for collections.

```kotlin
val set = setOf("apple", "banana", "orange")
set.assert()
    .hasSize(3)
    .contains("apple")
    .doesNotContain("grape")
```

##### `<T> Array<T>?.assert(): ObjectArrayAssert<T>`
Creates assertions for arrays.

```kotlin
val array = arrayOf("a", "b", "c")
array.assert()
    .hasSize(3)
    .contains("b")
    .doesNotContain("d")
```

##### `<T> List<T>??.assert(): ListAssert<T>`
Creates assertions for lists.

```kotlin
val items = listOf("apple", "banana", "orange")
items.assert()
    .hasSize(3)
    .contains("apple", "banana")
    .element(0).isEqualTo("apple")
```

##### `<T> Optional<T>?.assert(): OptionalAssert<T>`
Creates assertions for Optional values.

```kotlin
val present = Optional.of("value")
present.assert()
    .isPresent()
    .contains("value")

val empty = Optional.empty<String>()
empty.assert().isEmpty()
```

##### `<K, V> Map<K, V>?.assert(): MapAssert<K, V>`
Creates assertions for maps.

```kotlin
val map = mapOf("key1" to "value1", "key2" to "value2")
map.assert()
    .hasSize(2)
    .containsKey("key1")
    .containsValue("value1")
    .containsEntry("key1", "value1")
```

##### `<T> Stream<T>?.assert(): ListAssert<T>`
Creates assertions for streams (converted to lists).

```kotlin
val stream = listOf(1, 2, 3, 4, 5).stream()
stream.assert()
    .hasSize(5)
    .contains(3)
    .allMatch { it > 0 }
```

### Time and Date

##### `Date?.assert(): DateAssert`
Creates assertions for Date objects.

```kotlin
val date = Date()
date.assert()
    .isToday()
    .isBefore(Date(System.currentTimeMillis() + 1000))
```

##### `ZonedDateTime?.assert(): ZonedDateTimeAssert`
Creates assertions for ZonedDateTime objects.

```kotlin
val zonedDateTime = ZonedDateTime.now()
zonedDateTime.assert()
    .isToday()
    .hasZone(ZoneId.systemDefault())
```

##### `Temporal?.assert(): TemporalAssert`
Creates assertions for any Temporal objects.

```kotlin
val instant = Instant.now()
instant.assert()
    .isBefore(Instant.now().plusSeconds(1))
```

##### `LocalDateTime?.assert(): LocalDateTimeAssert`
Creates assertions for LocalDateTime objects.

```kotlin
val dateTime = LocalDateTime.now()
dateTime.assert()
    .isToday()
    .isBefore(LocalDateTime.now().plusHours(1))
```

##### `OffsetDateTime?.assert(): OffsetDateTimeAssert`
Creates assertions for OffsetDateTime objects.

```kotlin
val offsetDateTime = OffsetDateTime.now()
offsetDateTime.assert()
    .isToday()
    .hasOffset(ZoneOffset.UTC)
```

##### `OffsetTime?.assert(): OffsetTimeAssert`
Creates assertions for OffsetTime objects.

```kotlin
val offsetTime = OffsetTime.now()
offsetTime.assert()
    .isBefore(OffsetTime.now().plusHours(1))
```

##### `LocalTime?.assert(): LocalTimeAssert`
Creates assertions for LocalTime objects.

```kotlin
val time = LocalTime.of(10, 30)
time.assert()
    .isBefore(LocalTime.of(12, 0))
    .hasHour(10)
    .hasMinute(30)
```

##### `LocalDate?.assert(): LocalDateAssert`
Creates assertions for LocalDate objects.

```kotlin
val date = LocalDate.of(2023, 12, 25)
date.assert()
    .hasYear(2023)
    .hasMonth(12)
    .hasDayOfMonth(25)
```

##### `YearMonth?.assert(): YearMonthAssert`
Creates assertions for YearMonth objects.

```kotlin
val yearMonth = YearMonth.of(2023, 12)
yearMonth.assert()
    .hasYear(2023)
    .hasMonth(12)
```

##### `Instant?.assert(): InstantAssert`
Creates assertions for Instant objects.

```kotlin
val instant = Instant.now()
instant.assert()
    .isBefore(Instant.now().plusSeconds(1))
```

##### `Duration?.assert(): DurationAssert`
Creates assertions for Duration objects.

```kotlin
val duration = Duration.ofHours(2)
duration.assert()
    .hasHours(2)
    .isGreaterThan(Duration.ofHours(1))
```

##### `Period?.assert(): PeriodAssert`
Creates assertions for Period objects.

```kotlin
val period = Period.of(1, 2, 3)
period.assert()
    .hasYears(1)
    .hasMonths(2)
    .hasDays(3)
```

### File System and I/O

##### `Path?.assert(): PathAssert`
Creates assertions for Path objects.

```kotlin
val path = Paths.get("/tmp/test.txt")
path.assert()
    .exists()
    .isReadable()
    .isRegularFile()
```

##### `File?.assert(): FileAssert`
Creates assertions for File objects.

```kotlin
val file = File("/tmp/test.txt")
file.assert()
    .exists()
    .isFile()
    .canRead()
    .hasName("test.txt")
```

##### `URL?.assert(): UrlAssert`
Creates assertions for URL objects.

```kotlin
val url = URL("https://example.com")
url.assert()
    .hasHost("example.com")
    .hasProtocol("https")
    .hasPort(443)
```

##### `URI?.assert(): UriAssert`
Creates assertions for URI objects.

```kotlin
val uri = URI("https://example.com/path?query=value")
uri.assert()
    .hasHost("example.com")
    .hasPath("/path")
    .hasQuery("query=value")
```

### Concurrent Programming

##### `<V> Future<V>?.assert(): FutureAssert<V>`
Creates assertions for Future objects.

```kotlin
val future = executor.submit(Callable { "result" })
future.assert()
    .isDone()
    .isNotCancelled()
```

##### `<V> CompletableFuture<V>?.assert(): CompletableFutureAssert<V>`
Creates assertions for CompletableFuture objects.

```kotlin
val future = CompletableFuture.completedFuture("success")
future.assert()
    .isCompleted()
    .isCompletedWithValue("success")
```

##### `<V> CompletionStage<V>?.assert(): CompletionStageAssert<V>`
Creates assertions for CompletionStage objects.

```kotlin
val stage = CompletableFuture.completedFuture("result")
stage.assert()
    .isCompleted()
    .isCompletedWithValue("result")
```

### Functional Programming

##### `<T> Predicate<T>?.assert(): PredicateAssert<T>`
Creates assertions for Predicate functions.

```kotlin
val isEven = Predicate<Int> { it % 2 == 0 }
isEven.assert()
    .accepts(2, 4, 6)
    .rejects(1, 3, 5)
```

### Exception Testing

##### `<T : Throwable> T?.assert(): ThrowableAssert<T>`
Creates assertions for Throwable objects.

```kotlin
val exception = RuntimeException("test error")
exception.assert()
    .hasMessage("test error")
    .isInstanceOf(RuntimeException::class.java)
```

##### `assertThrownBy<T : Throwable>(Class<T>, () -> Unit): ThrowableAssert<T>`
Asserts that code throws a specific exception type.

```kotlin
assertThrownBy(IllegalArgumentException::class.java) {
    throw IllegalArgumentException("invalid argument")
}.assert().hasMessage("invalid argument")
```

##### `assertThrownBy<T : Throwable>(() -> Unit): ThrowableAssert<T>` (reified)
Asserts that code throws a specific exception type (Kotlin reified version).

```kotlin
assertThrownBy<IllegalArgumentException> {
    throw IllegalArgumentException("invalid argument")
}.assert().hasMessage("invalid argument")
```

## Comparison with AssertJ

FluentAssert is built on top of AssertJ and provides additional benefits:

| Feature | AssertJ | FluentAssert |
|---------|---------|--------------|
| **Syntax** | `assertThat(value).isEqualTo(expected)` | `value.assert().isEqualTo(expected)` |
| **Null Safety** | Manual null checks | Automatic null handling |
| **Kotlin Integration** | Java library | Kotlin-first design |
| **Extension Functions** | Not applicable | Full Kotlin extension support |
| **Type Inference** | Limited | Enhanced Kotlin type system |
| **IDE Support** | Good | Excellent (Kotlin-aware) |

### When to Use FluentAssert

- ✅ **Kotlin projects** - Better integration with Kotlin idioms
- ✅ **Null-heavy code** - Automatic null safety
- ✅ **Fluent style preference** - More readable assertion chains
- ✅ **Type-safe assertions** - Leverage Kotlin's type system

### When to Use AssertJ Directly

- 🔸 **Java projects** - No need for Kotlin extensions
- 🔸 **Custom assertion logic** - Direct AssertJ gives more control
- 🔸 **Performance-critical code** - Minimal overhead

## Advanced Examples

### Complex Business Logic Validation

```kotlin
// User registration validation
data class User(val name: String, val age: Int, val email: String)

fun validateUser(user: User) {
    user.name.assert()
        .isNotBlank()
        .hasSizeBetween(2, 50)
        .matches("[a-zA-Z\\s]+")

    user.age.assert()
        .isBetween(13, 120)

    user.email.assert()
        .isNotBlank()
        .matches("[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}")
}

// Usage
val user = User("John Doe", 25, "john@example.com")
validateUser(user)
```

### Data Processing Pipeline Testing

```kotlin
fun processData(input: List<String>): List<String> {
    return input
        .filter { it.isNotBlank() }
        .map { it.uppercase() }
        .distinct()
        .sorted()
}

fun testDataProcessing() {
    val input = listOf("  apple", "", "banana", "  APPLE", "cherry")
    val result = processData(input)

    result.assert()
        .hasSize(3)
        .contains("APPLE", "BANANA", "CHERRY")
        .isSorted()
        .allMatch { it == it.uppercase() }
}
```

### Concurrent Operation Testing

```kotlin
suspend fun testAsyncOperations() {
    val results = coroutineScope {
        val deferred1 = async { fetchUser(1) }
        val deferred2 = async { fetchUser(2) }

        listOf(deferred1, deferred2).awaitAll()
    }

    results.assert()
        .hasSize(2)
        .allMatch { it != null }
        .anySatisfy { user ->
            user.name.assert().isEqualTo("John Doe")
            user.id.assert().isPositive()
        }
}
```

### Configuration Validation

```kotlin
data class DatabaseConfig(
    val host: String,
    val port: Int,
    val database: String,
    val credentials: Map<String, String>
)

fun validateConfig(config: DatabaseConfig) {
    config.host.assert()
        .isNotBlank()
        .matches("^[a-zA-Z0-9.-]+$")

    config.port.assert()
        .isBetween(1024, 65535)

    config.database.assert()
        .isNotBlank()
        .hasSizeBetween(1, 64)

    config.credentials.assert()
        .isNotEmpty()
        .containsKey("username")
        .containsKey("password")
        .allSatisfy { (key, value) ->
            key.assert().isNotBlank()
            value.assert().isNotBlank()
        }
}
```

## Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Ahoo-Wang/FluentAssert.git
   cd FluentAssert
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run tests**
   ```bash
   ./gradlew test
   ```

4. **Run linting**
   ```bash
   ./gradlew detekt
   ```

### Code Style

- Follow Kotlin official coding conventions
- Use 300 characters maximum line length
- Write comprehensive tests for all public APIs
- Use descriptive test method names with backticks

### Pull Request Process

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Frequently Asked Questions

### General Questions

**Q: What is FluentAssert?**  
A: FluentAssert is a Kotlin library that provides fluent assertions for JDK types, making your unit tests more readable and expressive by wrapping AssertJ assertions with Kotlin extension functions.

**Q: Why should I use FluentAssert instead of AssertJ directly?**  
A: FluentAssert provides a more Kotlin-idiomatic API with better null safety, type inference, and IDE support. The `.assert()` syntax is more fluent and readable than AssertJ's `assertThat()`.

**Q: Is FluentAssert production-ready?**  
A: Yes, FluentAssert is stable and ready for production use. It has comprehensive test coverage and follows semantic versioning.

### Technical Questions

**Q: Does FluentAssert add runtime overhead?**  
A: Minimal. The extension functions are inlined where possible, and the library simply delegates to AssertJ, which is highly optimized.

**Q: Can I use FluentAssert with other testing frameworks?**  
A: Yes, FluentAssert works with any testing framework that supports AssertJ assertions, including JUnit 5, TestNG, and Spock.

**Q: How does null handling work?**  
A: All extension functions accept nullable types and handle null values appropriately, providing null-safe assertions without additional boilerplate.

**Q: What JDK versions are supported?**  
A: FluentAssert supports Java 17 and higher, with full compatibility with all modern JDK types and APIs.

### Usage Questions

**Q: How do I migrate from AssertJ to FluentAssert?**  
A: Replace `assertThat(value)` with `value.assert()`. The assertion methods remain the same.

**Q: Can I mix FluentAssert and AssertJ in the same test?**  
A: Yes, they are fully compatible. You can use both APIs in the same codebase.

**Q: Are there any limitations compared to AssertJ?**  
A: FluentAssert provides access to all AssertJ functionality. Some advanced AssertJ features may require direct AssertJ usage, but this is rare.

### Troubleshooting

**Q: I'm getting compilation errors. What should I check?**  
A: Ensure you're using Java 17+, Kotlin 1.8.0+, and have the correct dependencies. Check that your IDE is using the right JDK.

**Q: Tests are failing with null pointer exceptions.**  
A: This usually means you're calling methods on null values. Use safe calls (`?.`) or check for null before assertions.

**Q: IDE doesn't recognize the extension functions.**  
A: Ensure the FluentAssert dependency is properly configured and your IDE has Kotlin plugin updated.

### Contributing

**Q: How can I contribute new assertion types?**  
A: See our [Contributing Guide](CONTRIBUTING.md) for detailed instructions on adding new JDK type support.

**Q: Can I suggest new features?**  
A: Absolutely! Open an issue on GitHub with the "enhancement" label to discuss new features.

**Q: Found a bug. How do I report it?**  
A: Create an issue on GitHub with detailed steps to reproduce, expected vs actual behavior, and your environment details.

## License

FluentAssert is licensed under the [Apache License 2.0](LICENSE).
