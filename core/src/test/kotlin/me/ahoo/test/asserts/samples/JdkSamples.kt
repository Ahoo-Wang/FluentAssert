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

package me.ahoo.test.asserts.samples

import me.ahoo.test.asserts.assert
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import java.util.stream.Stream

/**
 * Executable samples rendered into the KDoc of `Jdk.kt` via `@sample`. Each function body is shown verbatim.
 */
class JdkSamples {

    @Test
    fun boolean() {
        val active = true
        active.assert().isTrue()

        val unknown: Boolean? = null
        unknown.assert().isNull()
    }

    @Test
    fun byte() {
        val value: Byte = 42
        value.assert().isEqualTo(42.toByte()).isPositive()
    }

    @Test
    fun short() {
        val value: Short = 1000
        value.assert().isEqualTo(1000.toShort()).isPositive()
    }

    @Test
    fun int() {
        val age = 25
        age.assert().isBetween(18, 65)
    }

    @Test
    fun long() {
        val size = 2_048L
        size.assert().isPositive().isGreaterThan(1_024L)
    }

    @Test
    fun float() {
        val ratio = 0.75f
        ratio.assert().isBetween(0f, 1f)
    }

    @Test
    fun double() {
        val price = 19.99
        price.assert().isCloseTo(20.0, Offset.offset(0.01))
    }

    @Test
    fun char() {
        val grade = 'A'
        grade.assert().isUpperCase().isEqualTo('A')
    }

    @Test
    fun bigDecimal() {
        val amount = BigDecimal("123.45")
        amount.assert().isEqualByComparingTo("123.450").isPositive()
    }

    @Test
    fun bigInteger() {
        val count = BigInteger("12345678901234567890")
        count.assert().isPositive().isGreaterThan(BigInteger.TEN)
    }

    @Test
    fun string() {
        val name = "FluentAssert"
        name.assert().startsWith("Fluent").endsWith("Assert").hasSize(12)
    }

    @Test
    fun stringBuilder() {
        val text = StringBuilder("Fluent").append("Assert")
        text.assert().startsWith("Fluent").hasSize(12)
    }

    @Test
    fun stringBuffer() {
        val text = StringBuffer("Fluent").append("Assert")
        text.assert().endsWith("Assert").hasSize(12)
    }

    @Test
    fun anyObject() {
        data class Person(val name: String, val age: Int)

        val person = Person("Ada", 36)
        person.assert().isEqualTo(Person("Ada", 36)).hasFieldOrPropertyWithValue("name", "Ada")
    }

    @Test
    fun iterable() {
        val numbers: Iterable<Int> = listOf(1, 2, 3)
        numbers.assert().containsExactly(1, 2, 3).allMatch { it > 0 }
    }

    @Test
    fun iterator() {
        val iterator = listOf(1, 2, 3).iterator()
        iterator.assert().hasNext()
    }

    @Test
    fun collection() {
        val fruits = setOf("apple", "banana")
        fruits.assert().hasSize(2).contains("apple").doesNotContain("grape")
    }

    @Test
    fun array() {
        val letters = arrayOf("a", "b", "c")
        letters.assert().hasSize(3).containsExactly("a", "b", "c")
    }

    @Test
    fun primitiveArrays() {
        intArrayOf(1, 2, 3).assert().containsExactly(1, 2, 3)
        longArrayOf(1, 2).assert().contains(2L)
        doubleArrayOf(1.0, 2.0).assert().hasSize(2)
        booleanArrayOf(true, false).assert().contains(true)
        byteArrayOf(1, 2).assert().startsWith(1)
        shortArrayOf(1, 2).assert().endsWith(2)
        floatArrayOf(1f).assert().containsOnly(1f)
        charArrayOf('a', 'b').assert().containsExactly('a', 'b')
    }

    @Test
    fun list() {
        val items = listOf("apple", "banana")
        items.assert().hasSize(2).first().isEqualTo("apple")
    }

    @Test
    fun optional() {
        Optional.of("value").assert().contains("value")
        Optional.empty<String>().assert().isEmpty()
    }

    @Test
    fun map() {
        val scores = mapOf("ada" to 90, "grace" to 95)
        scores.assert().hasSize(2).containsEntry("ada", 90).containsKey("grace")
    }

    @Test
    fun stream() {
        val stream = Stream.of(1, 2, 3)
        stream.assert().containsExactly(1, 2, 3)
    }

    @Test
    fun comparable() {
        data class Version(val value: Int) : Comparable<Version> {
            override fun compareTo(other: Version): Int = value.compareTo(other.value)
        }

        Version(2).assert().isGreaterThan(Version(1)).isLessThan(Version(3))
    }
}
