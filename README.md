# FluentAssert

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://github.com/Ahoo-Wang/FluentAssert/blob/main/LICENSE)
[![GitHub release](https://img.shields.io/github/release/Ahoo-Wang/FluentAssert.svg)](https://github.com/Ahoo-Wang/FluentAssert/releases)
[![Maven Central Version](https://img.shields.io/maven-central/v/me.ahoo.test/fluent-assert-core)](https://central.sonatype.com/artifact/me.ahoo.test/fluent-assert-core)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d58ca7a79749493ebb48d423e73b6e1d)](https://app.codacy.com/gh/Ahoo-Wang/FluentAssert/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![codecov](https://codecov.io/gh/Ahoo-Wang/FluentAssert/graph/badge.svg?token=iXUP7NXHSc)](https://codecov.io/gh/Ahoo-Wang/FluentAssert)
[![CI](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/ci.yml/badge.svg)](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/ci.yml)
[![CodeQL](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/codeql.yml/badge.svg)](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/codeql.yml)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/Ahoo-Wang/FluentAssert)

English | [简体中文](README.zh-CN.md)

Kotlin `.assert()` extensions for [AssertJ](https://assertj.github.io/doc/): write `value.assert()` instead of `assertThat(value)`.

```kotlin
import me.ahoo.test.asserts.assert

"FluentAssert".assert().startsWith("Fluent").hasSize(12)
```

FluentAssert is deliberately thin. Every extension returns the AssertJ assertion you would get from
`assertThat(...)`, so assertion methods, failure messages and behavior are AssertJ's own. What it adds:

- **Kotlin call sites**: start the chain from the value, including `null` values of a nullable type.
- **The most specific, concretely typed assertion for the static type**, including cases where `assertThat`
  does not even compile from Kotlin (`ScheduledFuture` is ambiguous between its `Future` and `Comparable`
  overloads).
- **`assertThrownBy<T> { }`**: a reified exception assertion.

## Installation

Requirements: JDK 17+, Kotlin 2.1+ compiler. AssertJ comes in as a transitive dependency.

```kotlin
// Gradle (Kotlin DSL)
testImplementation("me.ahoo.test:fluent-assert-core:1.2.1")
```

```xml
<!-- Maven -->
<dependency>
    <groupId>me.ahoo.test</groupId>
    <artifactId>fluent-assert-core</artifactId>
    <version>1.2.1</version>
    <scope>test</scope>
</dependency>
```

## Usage

```kotlin
import me.ahoo.test.asserts.assert
import me.ahoo.test.asserts.assertThrownBy

val name: String? = null
name.assert().isNull()

listOf("a", "b").assert().hasSize(2).containsExactly("a", "b")
mapOf("ada" to 90).assert().containsEntry("ada", 90)
intArrayOf(1, 2, 3).assert().contains(2)

assertThrownBy<IllegalArgumentException> {
    require(false) { "invalid" }
}.hasMessage("invalid")
```

Migrating from AssertJ is mechanical: `assertThat(x)` becomes `x.assert()` and
`assertThatThrownBy { }.isInstanceOf(T::class.java)` becomes `assertThrownBy<T> { }`. AssertJ entry points
without a receiver (`SoftAssertions`, `assertThatCode`, `assumeThat`, ...) are used directly and mix freely
with FluentAssert.

## Supported types

The overload is chosen from the **static** type of the receiver. Every receiver except `AssertProvider<A>`
may be `null`.

| Receiver | Returns |
|---|---|
| `Boolean`, `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`, `Char` | `BooleanAssert`, `ByteAssert`, `ShortAssert`, `IntegerAssert`, `LongAssert`, `FloatAssert`, `DoubleAssert`, `CharacterAssert` |
| `BigDecimal`, `BigInteger` | `BigDecimalAssert`, `BigIntegerAssert` |
| `String` | `StringAssert` |
| `StringBuilder`, `StringBuffer` | `CharSequenceAssert` |
| `List<T>` | `ListAssert<T>` |
| `Collection<T>` (e.g. `Set`) | `CollectionAssert<T>` |
| `Iterable<T>` | `IterableAssert<T>` |
| `Iterator<T>` | `IteratorAssert<T>` |
| `Map<K, V>` | `MapAssert<K, V>` |
| `Array<T>` | `ObjectArrayAssert<T>` |
| `BooleanArray`, `ByteArray`, `ShortArray`, `IntArray`, `LongArray`, `FloatArray`, `DoubleArray`, `CharArray` | `BooleanArrayAssert`, `ByteArrayAssert`, `ShortArrayAssert`, `IntArrayAssert`, `LongArrayAssert`, `FloatArrayAssert`, `DoubleArrayAssert`, `CharArrayAssert` |
| `Optional<T>` | `OptionalAssert<T>` |
| `Stream<T>` | `ListAssert<T>` (the stream is consumed) |
| `Date` | `DateAssert` |
| `Instant`, `LocalDate`, `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, `ZonedDateTime`, `YearMonth` | `InstantAssert`, `LocalDateAssert`, `LocalTimeAssert`, `LocalDateTimeAssert`, `OffsetTimeAssert`, `OffsetDateTimeAssert`, `ZonedDateTimeAssert`, `YearMonthAssert` |
| `Duration`, `Period` | `DurationAssert`, `PeriodAssert` |
| `Temporal` | `TemporalAssert` |
| `Path`, `File`, `URL`, `URI` | `PathAssert`, `FileAssert`, `UrlAssert`, `UriAssert` |
| `Future<V>`, `ScheduledFuture<V>` | `FutureAssert<V>` |
| `CompletableFuture<V>`, `CompletionStage<V>` | `CompletableFutureAssert<V>` |
| `Predicate<T>` | `PredicateAssert<T>` |
| `T : Throwable` | `ThrowableAssert<T>` |
| `AssertProvider<A>` | `A` |
| `T : Comparable<T>` (no more specific overload) | `GenericComparableAssert<T>` |
| any other `T` | `ObjectAssert<T>` |

Examples for every overload are in the [API documentation](https://javadoc.io/doc/me.ahoo.test/fluent-assert-core).

## Overload resolution rules

Kotlin picks the most specific overload for the **declared** type, which explains every surprise:

- **Declare the type you want to assert.** A `List` receiver gets `ListAssert`; declare it as `Iterable<T>` to
  get `IterableAssert`. A concrete `java.time` value gets its own assertion; only a `Temporal`-typed value
  gets `TemporalAssert`.
- **Text that is only known as `CharSequence` gets `ObjectAssert`.** There is deliberately no `CharSequence`
  overload, because types that are both `CharSequence` and `Comparable` (such as `CharBuffer`) would become
  ambiguous. Call `.toString()` first.
- **Primitive streams are not `Stream<T>`.** Use `intStream.boxed().assert()`.
- **A custom type that is `Comparable` and also `Iterable`, `Map`, `Future` or `Throwable` is ambiguous** and
  does not compile. Pick the overload by declaring the type:

```kotlin
class Batch(private val items: List<String>) : Iterable<String>, Comparable<Batch> {
    override fun iterator() = items.iterator()
    override fun compareTo(other: Batch) = items.size.compareTo(other.items.size)
}

val batch: Iterable<String> = Batch(listOf("a", "b"))
batch.assert().containsExactly("a", "b")
```

## Custom assertions

Types implementing AssertJ's `AssertProvider<A>` return their own assertion from `.assert()`:

```kotlin
class Money(private val amount: BigDecimal) : AssertProvider<BigDecimalAssert> {
    override fun assertThat(): BigDecimalAssert = amount.assert()
}

Money(BigDecimal("99.99")).assert().isPositive().isLessThan(BigDecimal("100"))
```

## AI coding assistants

[`skills/fluent-assert`](skills/fluent-assert/SKILL.md) is an agent skill that teaches coding assistants to write
FluentAssert assertions and avoid the resolution traps above.

## Documentation

| Document | Contents |
|---|---|
| [API documentation](https://javadoc.io/doc/me.ahoo.test/fluent-assert-core) | Every overload with an executable example |
| [CHANGELOG.md](CHANGELOG.md) | Release history and compatibility notes |
| [REQUIREMENTS.md](REQUIREMENTS.md) | Guarantees the library makes and the tests that verify them |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Building, testing, adding overloads, review and release process |
| [SECURITY.md](SECURITY.md) | Supported versions and vulnerability reporting |

## License

[Apache License 2.0](LICENSE)
