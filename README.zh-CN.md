# FluentAssert

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://github.com/Ahoo-Wang/FluentAssert/blob/main/LICENSE)
[![GitHub release](https://img.shields.io/github/release/Ahoo-Wang/FluentAssert.svg)](https://github.com/Ahoo-Wang/FluentAssert/releases)
[![Maven Central Version](https://img.shields.io/maven-central/v/me.ahoo.test/fluent-assert-core)](https://central.sonatype.com/artifact/me.ahoo.test/fluent-assert-core)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d58ca7a79749493ebb48d423e73b6e1d)](https://app.codacy.com/gh/Ahoo-Wang/FluentAssert/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![codecov](https://codecov.io/gh/Ahoo-Wang/FluentAssert/graph/badge.svg?token=iXUP7NXHSc)](https://codecov.io/gh/Ahoo-Wang/FluentAssert)
[![Integration Test Status](https://github.com/Ahoo-Wang/FluentAssert/actions/workflows/codecov.yml/badge.svg)](https://github.com/Ahoo-Wang/FluentAssert)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/Ahoo-Wang/FluentAssert)

FluentAssert是一个为JDK类型提供流畅断言的Kotlin库，使您的测试更易读和富有表现力。

## 快速开始

### Maven

```xml
<dependency>
    <groupId>me.ahoo.test</groupId>
    <artifactId>fluent-assert-core</artifactId>
    <version>latest-version</version>
    <scope>test</scope>
</dependency>
```

### Gradle

```kotlin
testImplementation("me.ahoo.test:fluent-assert-core:latest-version")
```

## 使用示例

### 基本断言

```kotlin
// 字符串断言
val name = "FluentAssert"
name.assert().startsWith("Fluent").endsWith("Assert").contains("uentAss")

// 数字断言
val age = 25
age.assert().isGreaterThan(18).isLessThan(60)

// 布尔值断言
val isActive = true
isActive.assert().isTrue()

// 列表断言
val items = listOf("apple", "banana", "orange")
items.assert().hasSize(3).contains("banana")
```

### 集合断言

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
numbers.assert()
    .isNotEmpty()
    .hasSize(5)
    .contains(3)
    .allSatisfy { it.assert().isPositive() }
```

### 异常断言

```kotlin
// 断言抛出特定异常
assertThrownBy<IllegalArgumentException> {
    throw IllegalArgumentException("Invalid argument")
}.assert().hasMessage("Invalid argument")

// 或者直接使用扩展函数
val exception = IllegalArgumentException("Invalid argument")
exception.assert().hasMessage("Invalid argument")
```

### 时间断言

```kotlin
val now = LocalDateTime.now()
val yesterday = now.minusDays(1)

now.assert().isAfter(yesterday)
```

### 并发断言

```kotlin
val future = CompletableFuture.completedFuture("result")
future.assert().isCompletedWithValue("result")
```