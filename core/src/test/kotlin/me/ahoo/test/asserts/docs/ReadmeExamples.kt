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
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * Verbatim copies of the Kotlin blocks in README.md and README.zh-CN.md; [DocumentationTest] fails when they drift.
 */
class ReadmeExamples {

    @Test
    fun intro() {
        "FluentAssert".assert().startsWith("Fluent").hasSize(12)
    }

    @Test
    fun usage() {
        val name: String? = null
        name.assert().isNull()

        listOf("a", "b").assert().hasSize(2).containsExactly("a", "b")
        mapOf("ada" to 90).assert().containsEntry("ada", 90)
        intArrayOf(1, 2, 3).assert().contains(2)

        assertThrownBy<IllegalArgumentException> {
            require(false) { "invalid" }
        }.hasMessage("invalid")
    }

    @Test
    fun ambiguousComparable() {
        class Batch(private val items: List<String>) : Iterable<String>, Comparable<Batch> {
            override fun iterator() = items.iterator()
            override fun compareTo(other: Batch) = items.size.compareTo(other.items.size)
        }

        val batch: Iterable<String> = Batch(listOf("a", "b"))
        batch.assert().containsExactly("a", "b")
    }

    @Test
    fun customAssertions() {
        class Money(private val amount: BigDecimal) : AssertProvider<BigDecimalAssert> {
            override fun assertThat(): BigDecimalAssert = amount.assert()
        }

        Money(BigDecimal("99.99")).assert().isPositive().isLessThan(BigDecimal("100"))
    }
}
