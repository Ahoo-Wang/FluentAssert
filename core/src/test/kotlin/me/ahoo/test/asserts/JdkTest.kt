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

package me.ahoo.test.asserts

import io.mockk.mockk
import org.assertj.core.api.BigDecimalAssert
import org.assertj.core.api.BigIntegerAssert
import org.assertj.core.api.BooleanAssert
import org.assertj.core.api.ByteAssert
import org.assertj.core.api.CharSequenceAssert
import org.assertj.core.api.CharacterAssert
import org.assertj.core.api.CollectionAssert
import org.assertj.core.api.DoubleAssert
import org.assertj.core.api.FloatAssert
import org.assertj.core.api.GenericComparableAssert
import org.assertj.core.api.IntArrayAssert
import org.assertj.core.api.IntegerAssert
import org.assertj.core.api.IterableAssert
import org.assertj.core.api.IteratorAssert
import org.assertj.core.api.ListAssert
import org.assertj.core.api.LongAssert
import org.assertj.core.api.MapAssert
import org.assertj.core.api.ObjectArrayAssert
import org.assertj.core.api.ObjectAssert
import org.assertj.core.api.OptionalAssert
import org.assertj.core.api.ShortAssert
import org.assertj.core.api.StringAssert
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Optional
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream

class JdkTest {

    @Test
    fun `given Boolean when assert then BooleanAssert`() {
        val value = true
        value.assert().assert().isInstanceOf(BooleanAssert::class.java)
    }

    @Test
    fun `given NullableBoolean when assert then BooleanAssert`() {
        val value: Boolean? = null
        value.assert().assert().isInstanceOf(BooleanAssert::class.java)
    }

    @Test
    fun `given Byte when assert then ByteAssert`() {
        val value: Byte = 1
        value.assert().assert().isInstanceOf(ByteAssert::class.java)
    }

    @Test
    fun `given Short when assert then ShortAssert`() {
        val value: Short = 1
        value.assert().assert().isInstanceOf(ShortAssert::class.java)
    }

    @Test
    fun `given Int when assert then IntegerAssert`() {
        val value: Int = 1
        value.assert().assert().isInstanceOf(IntegerAssert::class.java)
    }

    @Test
    fun `given Long when assert then LongAssert`() {
        val value: Long = 1
        value.assert().assert().isInstanceOf(LongAssert::class.java)
    }

    @Test
    fun `given Float when assert then FloatAssert`() {
        val value: Float = 1.0F
        value.assert().assert().isInstanceOf(FloatAssert::class.java)
    }

    @Test
    fun `given Double when assert then DoubleAssert`() {
        val value: Double = 1.0
        value.assert().assert().isInstanceOf(DoubleAssert::class.java)
    }

    @Test
    fun `given BigDecimal when assert then BigDecimalAssert`() {
        val value: BigDecimal = BigDecimal.TEN
        value.assert().assert().isInstanceOf(BigDecimalAssert::class.java)
    }

    @Test
    fun `given String when assert then StringAssert`() {
        val value: String = "1"
        value.assert().assert().isInstanceOf(StringAssert::class.java)
    }

    @Test
    fun `given Char when assert then CharacterAssert`() {
        val grade = 'A'
        grade.assert().isUpperCase().isEqualTo('A')
        grade.assert().assert().isInstanceOf(CharacterAssert::class.java)
    }

    @Test
    fun `given BigInteger when assert then BigIntegerAssert`() {
        val count = BigInteger("12345678901234567890")
        count.assert().isPositive().isGreaterThan(BigInteger.TEN)
        count.assert().assert().isInstanceOf(BigIntegerAssert::class.java)
    }

    @Test
    fun `given StringBuilder when assert then CharSequenceAssert`() {
        val text = StringBuilder("Fluent").append("Assert")
        text.assert().startsWith("Fluent").hasSize(12)
        text.assert().assert().isInstanceOf(CharSequenceAssert::class.java)
    }

    @Test
    fun `given StringBuffer when assert then CharSequenceAssert`() {
        val text = StringBuffer("Fluent").append("Assert")
        text.assert().endsWith("Assert").hasSize(12)
        text.assert().assert().isInstanceOf(CharSequenceAssert::class.java)
    }

    @Test
    fun `given primitive arrays when assert then primitive array asserts`() {
        booleanArrayOf(true, false).assert().contains(true)
        byteArrayOf(1, 2, 3).assert().hasSize(3).contains(2)
        shortArrayOf(1, 2, 3).assert().hasSize(3).contains(2)
        intArrayOf(1, 2, 3).assert().hasSize(3).containsExactly(1, 2, 3)
        longArrayOf(1, 2, 3).assert().hasSize(3).contains(2L)
        floatArrayOf(1f, 2f).assert().hasSize(2).contains(2f)
        doubleArrayOf(1.0, 2.0).assert().hasSize(2).contains(2.0)
        charArrayOf('a', 'b').assert().containsExactly('a', 'b')
        intArrayOf(1).assert().assert().isInstanceOf(IntArrayAssert::class.java)
    }

    @Test
    fun `given Object when assert then ObjectAssert`() {
        val value = Any()
        value.assert().assert().isInstanceOf(ObjectAssert::class.java)
    }

    @Test
    fun `given Iterator when assert then IteratorAssert`() {
        val value = mockk<Iterator<String>>()
        value.assert().assert().isInstanceOf(IteratorAssert::class.java)
    }

    @Test
    fun `given Iterable when assert then IterableAssert`() {
        val value = mockk<Iterable<String>>()
        value.assert().assert().isInstanceOf(IterableAssert::class.java)
    }

    @Test
    fun `given Collection when assert then CollectionAssert`() {
        val value = mockk<Collection<String>>()
        value.assert().assert().isInstanceOf(CollectionAssert::class.java)
    }

    @Test
    fun `given Array when assert then ObjectArrayAssert`() {
        val value = arrayOf("1")
        value.assert().assert().isInstanceOf(ObjectArrayAssert::class.java)
    }

    @Test
    fun `given vararg parameter when assert then content assertions accept its element type`() {
        fun check(vararg values: String) {
            values.assert().hasSize(2).contains("one")
        }
        check("one", "two")
    }

    @Test
    fun `given projected Array when assert then ObjectArrayAssert of the projection bound`() {
        val numbers: Array<out Number> = arrayOf(1, 2)
        val numbersAssert: ObjectArrayAssert<Number> = numbers.assert()
        numbersAssert.contains(1).hasSize(2)

        val anything: Array<*> = arrayOf("a", 1)
        val anythingAssert: ObjectArrayAssert<Any?> = anything.assert()
        anythingAssert.containsExactly("a", 1)

        val missing: Array<out Number>? = null
        missing.assert().isNull()
    }

    @Test
    fun `given List when assert then ListAssert`() {
        val value = listOf("1")
        value.assert().assert().isInstanceOf(ListAssert::class.java)
    }

    @Test
    fun `given NullableList when assert then ListAssert`() {
        val value: List<String>? = null
        val assertion = value.assert()
        assertion.isNull()
        assertion.assert().isInstanceOf(ListAssert::class.java)
    }

    @Test
    fun `given Optional when assert then OptionalAssert`() {
        val value = Optional.of("1")
        value.assert().assert().isInstanceOf(OptionalAssert::class.java)
    }

    @Test
    fun `given Map when assert then MapAssert`() {
        val value = mapOf("1" to "1")
        value.assert().assert().isInstanceOf(MapAssert::class.java)
    }

    @Test
    fun `given Stream when assert then ListAssert`() {
        val value = mockk<Stream<String>>()
        value.assert().assert().isInstanceOf(ListAssert::class.java)
    }

    @Test
    fun `given projected Stream when assert then ListAssert of the projection bound`() {
        val numbers: Stream<out Number> = Stream.of(1, 2)
        val numbersAssert: ListAssert<Number> = numbers.assert()
        numbersAssert.contains(1).hasSize(2)

        val anything: Stream<*> = Stream.of("a", 1)
        val anythingAssert: ListAssert<Any?> = anything.assert()
        anythingAssert.containsExactly("a", 1)

        val missing: Stream<out Number>? = null
        missing.assert().isNull()
    }

    @Test
    fun `given Stream when assert then the first content check consumes it`() {
        val visits = AtomicInteger()
        val assertion = Stream.of("one", "two").peek { visits.incrementAndGet() }.assert()
        visits.get().assert().isZero()

        assertion.isNotNull()
        visits.get().assert().isZero()

        assertion.containsExactly("one", "two")
        visits.get().assert().isEqualTo(2)

        val closedBeforeCheck = Stream.of("one").use { it.assert() }
        assertThrows(IllegalStateException::class.java) { closedBeforeCheck.containsExactly("one") }
    }

    @Test
    fun `given ComparableObj when assert then GenericComparableAssert`() {
        val value = ComparableObj("1")
        value.assert().assert().isInstanceOf(GenericComparableAssert::class.java)
    }

    @Test
    fun `given NullableComparableObj when assert then GenericComparableAssert`() {
        val value: ComparableObj? = null
        value.assert().assert().isInstanceOf(GenericComparableAssert::class.java)
    }

    @Test
    fun `given Comparable Iterable when upcast to Iterable then IterableAssert`() {
        // Comparable + Iterable is ambiguous for `value.assert()`; a statically typed receiver picks the overload.
        val value: Iterable<String> = ComparableIterable(listOf("1"))
        val assertion: IterableAssert<String> = value.assert()
        assertion.containsExactly("1")
    }

    class ComparableIterable(private val items: List<String>) : Iterable<String>, Comparable<ComparableIterable> {
        override fun iterator(): Iterator<String> = items.iterator()
        override fun compareTo(other: ComparableIterable): Int = items.size.compareTo(other.items.size)
    }

    class ComparableObj(val id: String) : Comparable<ComparableObj> {
        override fun compareTo(other: ComparableObj): Int {
            return this.id.compareTo(other.id)
        }
    }
}
