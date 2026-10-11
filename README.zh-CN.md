# FluentAssert

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://github.com/Ahoo-Wang/FluentAssert/blob/main/LICENSE)
[![GitHub release](https://img.shields.io/github/release/Ahoo-Wang/FluentAssert.svg)](https://github.com/Ahoo-Wang/FluentAssert/releases)
[![Maven Central Version](https://img.shields.io/maven-central/v/me.ahoo.test/fluent-assert-core)](https://central.sonatype.com/artifact/me.ahoo.test/fluent-assert-core)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d58ca7a79749493ebb48d423e73b6e1d)](https://app.codacy.com/gh/Ahoo-Wang/FluentAssert/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![codecov](https://codecov.io/gh/Ahoo-Wang/FluentAssert/graph/badge.svg?token=iXUP7NXHSc)](https://codecov.io/gh/Ahoo-Wang/FluentAssert)
[![CI](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/ci.yml/badge.svg)](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/ci.yml)
[![CodeQL](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/codeql.yml/badge.svg)](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/codeql.yml)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/Ahoo-Wang/FluentAssert)

[English](README.md) | 简体中文

为 [AssertJ](https://assertj.github.io/doc/) 提供 Kotlin `.assert()` 扩展：用 `value.assert()` 代替 `assertThat(value)`。

```kotlin
import me.ahoo.test.asserts.assert

"FluentAssert".assert().startsWith("Fluent").hasSize(12)
```

FluentAssert 刻意保持轻薄。每个扩展函数返回的都是 `assertThat(...)` 会返回的那个 AssertJ 断言对象，
因此断言方法、失败信息和行为都来自 AssertJ 本身。它额外提供的是：

- **Kotlin 风格的调用方式**：从值本身开始链式断言，可空类型的 `null` 值同样适用。
- **针对静态类型最具体、具体类型化的断言**，包括在 Kotlin 中 `assertThat` 根本无法编译的情况
  （`ScheduledFuture` 在 `Future` 与 `Comparable` 重载之间存在歧义）。
- **`assertThrownBy<T> { }`**：支持具体化泛型（reified）的异常断言。

## 安装

要求：JDK 17+，Kotlin 2.1+ 编译器。AssertJ 作为传递依赖自动引入。

```kotlin
// Gradle (Kotlin DSL)
testImplementation("me.ahoo.test:fluent-assert-core:1.2.0")
```

```xml
<!-- Maven -->
<dependency>
    <groupId>me.ahoo.test</groupId>
    <artifactId>fluent-assert-core</artifactId>
    <version>1.2.0</version>
    <scope>test</scope>
</dependency>
```

## 使用

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

从 AssertJ 迁移是机械替换：`assertThat(x)` 改为 `x.assert()`，
`assertThatThrownBy { }.isInstanceOf(T::class.java)` 改为 `assertThrownBy<T> { }`。
没有接收者的 AssertJ 入口（`SoftAssertions`、`assertThatCode`、`assumeThat` 等）直接使用即可，可与 FluentAssert 混用。

## 支持的类型

重载由接收者的**静态**类型决定。除 `AssertProvider<A>` 外，所有接收者都可以为 `null`。

| 接收者 | 返回 |
|---|---|
| `Boolean`, `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`, `Char` | `BooleanAssert`, `ByteAssert`, `ShortAssert`, `IntegerAssert`, `LongAssert`, `FloatAssert`, `DoubleAssert`, `CharacterAssert` |
| `BigDecimal`, `BigInteger` | `BigDecimalAssert`, `BigIntegerAssert` |
| `String` | `StringAssert` |
| `StringBuilder`, `StringBuffer` | `CharSequenceAssert` |
| `List<T>` | `ListAssert<T>` |
| `Collection<T>`（如 `Set`） | `CollectionAssert<T>` |
| `Iterable<T>` | `IterableAssert<T>` |
| `Iterator<T>` | `IteratorAssert<T>` |
| `Map<K, V>` | `MapAssert<K, V>` |
| `Array<T>` | `ObjectArrayAssert<T>` |
| `BooleanArray`, `ByteArray`, `ShortArray`, `IntArray`, `LongArray`, `FloatArray`, `DoubleArray`, `CharArray` | `BooleanArrayAssert`, `ByteArrayAssert`, `ShortArrayAssert`, `IntArrayAssert`, `LongArrayAssert`, `FloatArrayAssert`, `DoubleArrayAssert`, `CharArrayAssert` |
| `Optional<T>` | `OptionalAssert<T>` |
| `Stream<T>` | `ListAssert<T>`（会消费该流） |
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
| `T : Comparable<T>`（没有更具体的重载时） | `GenericComparableAssert<T>` |
| 其他任意 `T` | `ObjectAssert<T>` |

每个重载的可执行示例见 [API 文档](https://javadoc.io/doc/me.ahoo.test/fluent-assert-core)。

## 重载解析规则

Kotlin 会为**声明**类型选择最具体的重载，所有"意外"都源于此：

- **声明你想断言的类型。** `List` 接收者得到 `ListAssert`；声明为 `Iterable<T>` 才得到 `IterableAssert`。
  具体的 `java.time` 类型得到各自的断言；只有声明为 `Temporal` 的值才得到 `TemporalAssert`。
- **只知道是 `CharSequence` 的文本得到 `ObjectAssert`。** 这里刻意没有提供 `CharSequence` 重载，
  因为同时实现 `CharSequence` 与 `Comparable` 的类型（如 `CharBuffer`）会产生歧义。请先调用 `.toString()`。
- **原生类型流不是 `Stream<T>`。** 请使用 `intStream.boxed().assert()`。
- **同时实现 `Comparable` 与 `Iterable`、`Map`、`Future` 或 `Throwable` 的自定义类型会产生歧义**，无法编译。
  请通过声明类型来选择重载：

```kotlin
class Batch(private val items: List<String>) : Iterable<String>, Comparable<Batch> {
    override fun iterator() = items.iterator()
    override fun compareTo(other: Batch) = items.size.compareTo(other.items.size)
}

val batch: Iterable<String> = Batch(listOf("a", "b"))
batch.assert().containsExactly("a", "b")
```

## 自定义断言

实现了 AssertJ `AssertProvider<A>` 的类型，调用 `.assert()` 会返回其自身提供的断言：

```kotlin
class Money(private val amount: BigDecimal) : AssertProvider<BigDecimalAssert> {
    override fun assertThat(): BigDecimalAssert = amount.assert()
}

Money(BigDecimal("99.99")).assert().isPositive().isLessThan(BigDecimal("100"))
```

## AI 编程助手

[`skills/fluent-assert`](skills/fluent-assert/SKILL.md) 是一个 Agent Skill，指导编程助手编写 FluentAssert 断言，并避开上述重载解析陷阱。

## 文档

| 文档 | 内容 |
|---|---|
| [API 文档](https://javadoc.io/doc/me.ahoo.test/fluent-assert-core) | 每个重载及其可执行示例 |
| [CHANGELOG.md](CHANGELOG.md) | 版本历史与兼容性说明 |
| [REQUIREMENTS.md](REQUIREMENTS.md) | 库提供的保证以及验证它们的测试 |
| [CONTRIBUTING.md](CONTRIBUTING.md) | 构建、测试、新增重载、评审与发布流程 |
| [SECURITY.md](SECURITY.md) | 受支持版本与漏洞报告方式 |

## 许可证

[Apache License 2.0](LICENSE)
