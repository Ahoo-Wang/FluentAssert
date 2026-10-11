/*
 * Copyright [2021-present] [ahoo wang <ahoowang@qq.com> (https://github.com/Ahoo-Wang)].
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ahoo.test.asserts.docs

import me.ahoo.test.asserts.assert
import me.ahoo.test.asserts.assertThrownBy
import org.assertj.core.api.AssertProvider
import org.assertj.core.api.BigDecimalAssert
import org.assertj.core.api.StringAssert
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.temporal.Temporal
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.Predicate

/**
 * Verbatim copies of the Kotlin blocks in skills/fluent-assert (SKILL.md, FULL-API.md); [DocumentationTest] fails
 * when they drift. Helpers above each block only define names the documentation leaves to the reader.
 */
class SkillExamples {

    private data class Order(val id: String, val total: BigDecimal)

    private fun parse(input: String): Int {
        require(input.isNotEmpty()) { "invalid input" }
        return input.toInt()
    }

    @Test
    fun coreRule() {
        val actual = Order("1", BigDecimal.TEN)
        val expected = Order("1", BigDecimal.TEN)
        actual.assert().isEqualTo(expected)
    }

    @Test
    fun nullableReceiver() {
        val name: String? = null
        name.assert().isNull()
    }

    @Test
    fun assertProviderPattern() {
        val provider = AssertProvider<BigDecimalAssert> { BigDecimal.ONE.assert() }
        provider.assert().isPositive()
    }

    @Test
    fun assertThrownByReified() {
        assertThrownBy<IllegalArgumentException> {
            parse("")
        }.hasMessage("invalid input")
    }

    @Test
    fun assertThrownByClass() {
        assertThrownBy(IllegalArgumentException::class.java) {
            parse("")
        }.hasMessage("invalid input")
    }

    @Test
    fun delegateCustomAssertions() {
        class StringAssertProvider(private val value: String) : AssertProvider<StringAssert> {
            override fun assertThat(): StringAssert = value.assert()
        }

        StringAssertProvider("ready").assert().isEqualTo("ready")
    }

    @Test
    fun nullableValuesAndCollections() {
        val name: String? = null
        name.assert().isNull()

        val items = listOf("a", "b")
        items.assert().hasSize(2).containsExactly("a", "b")
    }

    @Test
    fun comparableFallback() {
        class Version(val value: Int) : Comparable<Version> {
            override fun compareTo(other: Version): Int = value.compareTo(other.value)
        }

        Version(2).assert().isGreaterThan(Version(1))
    }

    @Test
    fun staticReceiverTypes() {
        val values: Iterable<Int> = listOf(1, 2, 3)
        values.assert().hasSize(3) // IterableAssert<Int>

        val temporal: Temporal = Instant.parse("2024-01-01T00:00:00Z")
        temporal.assert().isEqualTo(Instant.parse("2024-01-01T00:00:00Z")) // TemporalAssert

        val stage: CompletionStage<String> = CompletableFuture.completedFuture("done")
        stage.assert().isCompletedWithValue("done") // CompletableFutureAssert<String>
    }

    @Test
    fun timePredicatesAndRecursiveComparison() {
        val actual = Order("1", BigDecimal.TEN)
        val expected = Order("1", BigDecimal.TEN)

        LocalDate.of(2024, 4, 15).assert().hasMonth(Month.APRIL)

        val isEven = Predicate<Int> { it % 2 == 0 }
        isEven.assert().accepts(2, 4).rejects(1, 3)

        actual.assert().usingRecursiveComparison().isEqualTo(expected)
    }
}
