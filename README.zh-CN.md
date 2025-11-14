# FluentAssert

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://github.com/Ahoo-Wang/FluentAssert/blob/main/LICENSE)
[![GitHub release](https://img.shields.io/github/release/Ahoo-Wang/FluentAssert.svg)](https://github.com/Ahoo-Wang/FluentAssert/releases)
[![Maven Central Version](https://img.shields.io/maven-central/v/me.ahoo.test/fluent-assert-core)](https://central.sonatype.com/artifact/me.ahoo.test/fluent-assert-core)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d58ca7a79749493ebb48d423e73b6e1d)](https://app.codacy.com/gh/Ahoo-Wang/FluentAssert/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![codecov](https://codecov.io/gh/Ahoo-Wang/FluentAssert/graph/badge.svg?token=iXUP7NXHSc)](https://codecov.io/gh/Ahoo-Wang/FluentAssert)
[![Integration Test Status](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/codecov.yml/badge.svg)](https://github.com/Ahoo-Wang/FluentAssert)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/Ahoo-Wang/FluentAssert)

FluentAssert是一个为JDK类型提供流畅断言的Kotlin库，使您的测试更易读和富有表现力。该库使用Kotlin扩展函数包装AssertJ断言以获得更好的语法。

## 特性

- **流畅API**：以可读、自然的方式链式断言
- **空安全**：所有扩展函数都适当地处理可空类型
- **全面覆盖**：支持所有主要的JDK类型和集合
- **类型安全**：完整的Kotlin类型系统集成
- **AssertJ驱动**：利用强大的AssertJ断言库

## 安装

### 要求

- **Java**：17 或更高版本
- **Kotlin**：1.8.0 或更高版本
- **JUnit**：5.x（用于测试）

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

## 快速开始

只需在任何JDK类型上调用`.assert()`即可开始构建流畅断言：

```kotlin
import me.ahoo.test.asserts.assert

// 基本断言
val name = "FluentAssert"
name.assert().startsWith("Fluent").endsWith("Assert")

val age = 25
age.assert().isGreaterThan(18).isLessThan(100)

val isActive = true
isActive.assert().isTrue()
```

## API参考

### 核心扩展函数

所有扩展函数都遵循`Type.assert(): AssertJTypeAssert`模式，其中：

- `Type`是任何支持的JDK类型（可空或不可空）
- `AssertJTypeAssert`是相应的AssertJ断言类

### 支持的类型

| 类别        | 类型                                                                                                                                                           |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **基本类型**  | `Boolean`, `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`, `BigDecimal`                                                                                   |
| **文本**    | `String`                                                                                                                                                     |
| **集合**    | `Iterable<T>`, `Iterator<T>`, `Collection<T>`, `Array<T>`, `List<T>`, `Map<K,V>`, `Optional<T>`, `Stream<T>`                                                 |
| **时间/日期** | `Date`, `ZonedDateTime`, `LocalDateTime`, `OffsetDateTime`, `OffsetTime`, `LocalTime`, `LocalDate`, `YearMonth`, `Instant`, `Duration`, `Period`, `Temporal` |
| **I/O**   | `Path`, `File`, `URL`, `URI`                                                                                                                                 |
| **并发**    | `Future<V>`, `CompletableFuture<V>`, `CompletionStage<V>`                                                                                                    |
| **函数式**   | `Predicate<T>`                                                                                                                                               |
| **异常**    | `Throwable`                                                                                                                                                  |

### 异常测试函数

- `assertThrownBy<T : Throwable>(shouldRaiseThrowable: () -> Unit): ThrowableAssert<T>` - 断言代码抛出特定类型的异常
- `Throwable.assert(): ThrowableAssert<Throwable>` - 为异常实例创建断言

## 完整API参考

### 核心JDK类型

#### 基本类型

##### `Boolean?.assert(): BooleanAssert`
为布尔值创建断言。

```kotlin
true.assert().isTrue()
false.assert().isFalse()
val nullableBool: Boolean? = null
nullableBool.assert().isNull()
```

##### `Byte?.assert(): ByteAssert`
为字节值创建断言。

```kotlin
val value: Byte = 42
value.assert().isEqualTo(42).isPositive()
```

##### `Short?.assert(): ShortAssert`
为短整型值创建断言。

```kotlin
val value: Short = 1000
value.assert().isEqualTo(1000).isGreaterThan(0)
```

##### `Int?.assert(): IntegerAssert`
为整型值创建断言。

```kotlin
val age = 25
age.assert().isEqualTo(25).isBetween(18, 65)
```

##### `Long?.assert(): LongAssert`
为长整型值创建断言。

```kotlin
val timestamp = System.currentTimeMillis()
timestamp.assert().isPositive().isGreaterThan(0)
```

##### `Float?.assert(): FloatAssert`
为浮点数值创建断言。

```kotlin
val pi = 3.14f
pi.assert().isEqualTo(3.14f).isPositive()
```

##### `Double?.assert(): DoubleAssert`
为双精度浮点数值创建断言。

```kotlin
val price = 19.99
price.assert().isEqualTo(19.99).isPositive()
```

##### `BigDecimal?.assert(): BigDecimalAssert`
为BigDecimal值创建断言。

```kotlin
val amount = BigDecimal("123.45")
amount.assert().isEqualTo("123.45").isPositive()
```

#### 文本类型

##### `String?.assert(): StringAssert`
为字符串值创建断言。

```kotlin
val name = "FluentAssert"
name.assert()
    .startsWith("Fluent")
    .endsWith("Assert")
    .contains("uentAss")
    .hasLength(11)
```

#### 泛型类型

##### `<T> T?.assert(): ObjectAssert<T>`
为任何对象类型创建断言。

```kotlin
val person = Person("John", 30)
person.assert()
    .isNotNull()
    .hasFieldOrPropertyWithValue("name", "John")
```

##### `<T : Comparable<T>?> T.assert(): GenericComparableAssert<T>`
为可比较对象创建断言。

```kotlin
val version = "2.0.0"
version.assert()
    .isGreaterThan("1.0.0")
    .isLessThan("3.0.0")
```

### 集合

##### `<T> Iterable<T>?.assert(): IterableAssert<T>`
为可迭代集合创建断言。

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
numbers.assert()
    .hasSize(5)
    .contains(3)
    .doesNotContain(6)
    .allMatch { it > 0 }
```

##### `<T> Iterator<T>?.assert(): IteratorAssert<T>`
为迭代器创建断言。

```kotlin
val iterator = listOf(1, 2, 3).iterator()
iterator.assert().hasNext()
```

##### `<T> Collection<T>?.assert(): CollectionAssert<T>`
为集合创建断言。

```kotlin
val set = setOf("apple", "banana", "orange")
set.assert()
    .hasSize(3)
    .contains("apple")
    .doesNotContain("grape")
```

##### `<T> Array<T>?.assert(): ObjectArrayAssert<T>`
为数组创建断言。

```kotlin
val array = arrayOf("a", "b", "c")
array.assert()
    .hasSize(3)
    .contains("b")
    .doesNotContain("d")
```

##### `<T> List<T>??.assert(): ListAssert<T>`
为列表创建断言。

```kotlin
val items = listOf("apple", "banana", "orange")
items.assert()
    .hasSize(3)
    .contains("apple", "banana")
    .element(0).isEqualTo("apple")
```

##### `<T> Optional<T>?.assert(): OptionalAssert<T>`
为Optional值创建断言。

```kotlin
val present = Optional.of("value")
present.assert()
    .isPresent()
    .contains("value")

val empty = Optional.empty<String>()
empty.assert().isEmpty()
```

##### `<K, V> Map<K, V>?.assert(): MapAssert<K, V>`
为映射创建断言。

```kotlin
val map = mapOf("key1" to "value1", "key2" to "value2")
map.assert()
    .hasSize(2)
    .containsKey("key1")
    .containsValue("value1")
    .containsEntry("key1", "value1")
```

##### `<T> Stream<T>?.assert(): ListAssert<T>`
为流创建断言（转换为列表）。

```kotlin
val stream = listOf(1, 2, 3, 4, 5).stream()
stream.assert()
    .hasSize(5)
    .contains(3)
    .allMatch { it > 0 }
```

### 时间和日期

##### `Date?.assert(): DateAssert`
为Date对象创建断言。

```kotlin
val date = Date()
date.assert()
    .isToday()
    .isBefore(Date(System.currentTimeMillis() + 1000))
```

##### `ZonedDateTime?.assert(): ZonedDateTimeAssert`
为ZonedDateTime对象创建断言。

```kotlin
val zonedDateTime = ZonedDateTime.now()
zonedDateTime.assert()
    .isToday()
    .hasZone(ZoneId.systemDefault())
```

##### `Temporal?.assert(): TemporalAssert`
为任何Temporal对象创建断言。

```kotlin
val instant = Instant.now()
instant.assert()
    .isBefore(Instant.now().plusSeconds(1))
```

##### `LocalDateTime?.assert(): LocalDateTimeAssert`
为LocalDateTime对象创建断言。

```kotlin
val dateTime = LocalDateTime.now()
dateTime.assert()
    .isToday()
    .isBefore(LocalDateTime.now().plusHours(1))
```

##### `OffsetDateTime?.assert(): OffsetDateTimeAssert`
为OffsetDateTime对象创建断言。

```kotlin
val offsetDateTime = OffsetDateTime.now()
offsetDateTime.assert()
    .isToday()
    .hasOffset(ZoneOffset.UTC)
```

##### `OffsetTime?.assert(): OffsetTimeAssert`
为OffsetTime对象创建断言。

```kotlin
val offsetTime = OffsetTime.now()
offsetTime.assert()
    .isBefore(OffsetTime.now().plusHours(1))
```

##### `LocalTime?.assert(): LocalTimeAssert`
为LocalTime对象创建断言。

```kotlin
val time = LocalTime.of(10, 30)
time.assert()
    .isBefore(LocalTime.of(12, 0))
    .hasHour(10)
    .hasMinute(30)
```

##### `LocalDate?.assert(): LocalDateAssert`
为LocalDate对象创建断言。

```kotlin
val date = LocalDate.of(2023, 12, 25)
date.assert()
    .hasYear(2023)
    .hasMonth(12)
    .hasDayOfMonth(25)
```

##### `YearMonth?.assert(): YearMonthAssert`
为YearMonth对象创建断言。

```kotlin
val yearMonth = YearMonth.of(2023, 12)
yearMonth.assert()
    .hasYear(2023)
    .hasMonth(12)
```

##### `Instant?.assert(): InstantAssert`
为Instant对象创建断言。

```kotlin
val instant = Instant.now()
instant.assert()
    .isBefore(Instant.now().plusSeconds(1))
```

##### `Duration?.assert(): DurationAssert`
为Duration对象创建断言。

```kotlin
val duration = Duration.ofHours(2)
duration.assert()
    .hasHours(2)
    .isGreaterThan(Duration.ofHours(1))
```

##### `Period?.assert(): PeriodAssert`
为Period对象创建断言。

```kotlin
val period = Period.of(1, 2, 3)
period.assert()
    .hasYears(1)
    .hasMonths(2)
    .hasDays(3)
```

### 文件系统和I/O

##### `Path?.assert(): PathAssert`
为Path对象创建断言。

```kotlin
val path = Paths.get("/tmp/test.txt")
path.assert()
    .exists()
    .isReadable()
    .isRegularFile()
```

##### `File?.assert(): FileAssert`
为File对象创建断言。

```kotlin
val file = File("/tmp/test.txt")
file.assert()
    .exists()
    .isFile()
    .canRead()
    .hasName("test.txt")
```

##### `URL?.assert(): UrlAssert`
为URL对象创建断言。

```kotlin
val url = URL("https://example.com")
url.assert()
    .hasHost("example.com")
    .hasProtocol("https")
    .hasPort(443)
```

##### `URI?.assert(): UriAssert`
为URI对象创建断言。

```kotlin
val uri = URI("https://example.com/path?query=value")
uri.assert()
    .hasHost("example.com")
    .hasPath("/path")
    .hasQuery("query=value")
```

### 并发编程

##### `<V> Future<V>?.assert(): FutureAssert<V>`
为Future对象创建断言。

```kotlin
val future = executor.submit(Callable { "result" })
future.assert()
    .isDone()
    .isNotCancelled()
```

##### `<V> CompletableFuture<V>?.assert(): CompletableFutureAssert<V>`
为CompletableFuture对象创建断言。

```kotlin
val future = CompletableFuture.completedFuture("success")
future.assert()
    .isCompleted()
    .isCompletedWithValue("success")
```

##### `<V> CompletionStage<V>?.assert(): CompletionStageAssert<V>`
为CompletionStage对象创建断言。

```kotlin
val stage = CompletableFuture.completedFuture("result")
stage.assert()
    .isCompleted()
    .isCompletedWithValue("result")
```

### 函数式编程

##### `<T> Predicate<T>?.assert(): PredicateAssert<T>`
为Predicate函数创建断言。

```kotlin
val isEven = Predicate<Int> { it % 2 == 0 }
isEven.assert()
    .accepts(2, 4, 6)
    .rejects(1, 3, 5)
```

### 异常测试

##### `<T : Throwable> T?.assert(): ThrowableAssert<T>`
为Throwable对象创建断言。

```kotlin
val exception = RuntimeException("test error")
exception.assert()
    .hasMessage("test error")
    .isInstanceOf(RuntimeException::class.java)
```

##### `assertThrownBy<T : Throwable>(Class<T>, () -> Unit): ThrowableAssert<T>`
断言代码抛出特定类型的异常。

```kotlin
assertThrownBy(IllegalArgumentException::class.java) {
    throw IllegalArgumentException("invalid argument")
}.assert().hasMessage("invalid argument")
```

##### `assertThrownBy<T : Throwable>(() -> Unit): ThrowableAssert<T>` (reified)
断言代码抛出特定类型的异常（Kotlin reified版本）。

```kotlin
assertThrownBy<IllegalArgumentException> {
    throw IllegalArgumentException("invalid argument")
}.assert().hasMessage("invalid argument")
```

## 高级示例

### 复杂业务逻辑验证

```kotlin
// 用户注册验证
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

// 使用
val user = User("John Doe", 25, "john@example.com")
validateUser(user)
```

### 数据处理管道测试

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

### 并发操作测试

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

### 配置验证

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

## 贡献

我们欢迎贡献！请查看我们的[贡献指南](CONTRIBUTING.md)了解详情。

### 面向AI助手

本项目包含对LLM友好的文档文件：
- **[`llms.txt`](llms.txt)**：面向AI助手的简洁项目概述
- **[`llms-full.txt`](llms-full.txt)**：完整的API参考和技术细节
- **[`AGENTS.md`](AGENTS.md)**：面向编码代理的开发指南

在贡献代码时，请使用这些文件来了解项目结构、API模式和编码标准。

### 开发环境设置

1. **克隆仓库**
   ```bash
   git clone https://github.com/Ahoo-Wang/FluentAssert.git
   cd FluentAssert
   ```

2. **构建项目**
   ```bash
   ./gradlew build
   ```

3. **运行测试**
   ```bash
   ./gradlew test
   ```

4. **运行代码检查**
   ```bash
   ./gradlew detekt
   ```

### 代码风格

- 遵循Kotlin官方编码约定
- 使用300字符最大行长度
- 为所有公共API编写全面的测试
- 使用带反引号的描述性测试方法名称

### 拉取请求流程

1. Fork仓库
2. 创建功能分支（`git checkout -b feature/amazing-feature`）
3. 提交更改（`git commit -m 'Add amazing feature'`）
4. 推送到分支（`git push origin feature/amazing-feature`）
5. 开启拉取请求

## 常见问题解答

### 一般问题

**问：什么是FluentAssert？**  
答：FluentAssert是一个为JDK类型提供流畅断言的Kotlin库，通过使用Kotlin扩展函数包装AssertJ断言，使您的单元测试更易读和富有表现力。

**问：为什么应该使用FluentAssert而不是直接使用AssertJ？**  
答：FluentAssert提供了更符合Kotlin习惯的API，具有更好的空安全、类型推断和IDE支持。`.assert()`语法比AssertJ的`assertThat()`更流畅和可读。

**问：FluentAssert是否已准备好用于生产环境？**  
答：是的，FluentAssert稳定且可用于生产环境。它具有全面的测试覆盖率并遵循语义化版本控制。

### 技术问题

**问：FluentAssert会增加运行时开销吗？**  
答：最小的。扩展函数在可能的地方被内联，该库只是委托给高度优化的AssertJ。

**问：可以将FluentAssert与其他测试框架一起使用吗？**  
答：是的，FluentAssert适用于任何支持AssertJ断言的测试框架，包括JUnit 5、TestNG和Spock。

**问：空值处理是如何工作的？**  
答：所有扩展函数都接受可空类型并适当地处理空值，而无需额外的样板代码。

**问：支持哪些JDK版本？**  
答：FluentAssert支持Java 17及更高版本，与所有现代JDK类型和API完全兼容。

### 使用问题

**问：如何从AssertJ迁移到FluentAssert？**  
答：将`assertThat(value)`替换为`value.assert()`。断言方法保持不变。

**问：可以在同一个测试中混合使用FluentAssert和AssertJ吗？**  
答：是的，它们完全兼容。您可以在同一代码库中使用两种API。

**问：与AssertJ相比有什么限制吗？**  
答：FluentAssert提供对所有AssertJ功能的访问。某些高级AssertJ功能可能需要直接使用AssertJ，但这种情况很少见。

### 故障排除

**问：我遇到编译错误。应该检查什么？**  
答：确保您使用的是Java 17+、Kotlin 1.8.0+，并且具有正确的依赖项。检查您的IDE是否使用了正确的JDK。

**问：测试因空指针异常而失败。**  
答：这通常意味着您在空值上调用方法。使用安全调用（`?.`）或在断言之前检查空值。

**问：IDE不识别扩展函数。**  
答：确保FluentAssert依赖项已正确配置，并且您的IDE具有更新的Kotlin插件。

### 贡献

**问：如何贡献新的断言类型？**  
答：请查看我们的[贡献指南](CONTRIBUTING.md)以获取添加新的JDK类型支持的详细说明。

**问：可以建议新功能吗？**  
答：当然可以！在GitHub上使用"enhancement"标签创建一个issue来讨论新功能。

**问：发现了一个bug。如何报告？**  
答：在GitHub上创建一个issue，包含详细的重现步骤、预期与实际行为，以及您的环境详细信息。

## 许可证

FluentAssert采用[Apache License 2.0](LICENSE)许可证。
