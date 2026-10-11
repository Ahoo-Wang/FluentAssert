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

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.BigDecimalAssert
import org.assertj.core.api.BigIntegerAssert
import org.assertj.core.api.BooleanArrayAssert
import org.assertj.core.api.BooleanAssert
import org.assertj.core.api.ByteArrayAssert
import org.assertj.core.api.ByteAssert
import org.assertj.core.api.CharArrayAssert
import org.assertj.core.api.CharSequenceAssert
import org.assertj.core.api.CharacterAssert
import org.assertj.core.api.CollectionAssert
import org.assertj.core.api.DoubleArrayAssert
import org.assertj.core.api.DoubleAssert
import org.assertj.core.api.FloatArrayAssert
import org.assertj.core.api.FloatAssert
import org.assertj.core.api.GenericComparableAssert
import org.assertj.core.api.IntArrayAssert
import org.assertj.core.api.IntegerAssert
import org.assertj.core.api.IterableAssert
import org.assertj.core.api.IteratorAssert
import org.assertj.core.api.ListAssert
import org.assertj.core.api.LongArrayAssert
import org.assertj.core.api.LongAssert
import org.assertj.core.api.MapAssert
import org.assertj.core.api.ObjectArrayAssert
import org.assertj.core.api.ObjectAssert
import org.assertj.core.api.OptionalAssert
import org.assertj.core.api.ShortArrayAssert
import org.assertj.core.api.ShortAssert
import org.assertj.core.api.StringAssert
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import java.util.stream.Stream

/**
 * Creates a fluent assertion for Boolean values.
 *
 * This extension function provides access to AssertJ's BooleanAssert methods for fluent testing
 * of boolean values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val isActive = true
 * isActive.assert().isTrue()
 *
 * val nullableBool: Boolean? = null
 * nullableBool.assert().isNull()
 * ```
 *
 * @receiver Boolean? The boolean value to assert on (nullable)
 * @return BooleanAssert A fluent assertion object for boolean values
 */
fun Boolean?.assert(): BooleanAssert = BooleanAssert(this)

/**
 * Creates a fluent assertion for Byte values.
 *
 * This extension function provides access to AssertJ's ByteAssert methods for fluent testing
 * of byte values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val value: Byte = 42
 * value.assert().isEqualTo(42)
 * ```
 *
 * @receiver Byte? The byte value to assert on (nullable)
 * @return ByteAssert A fluent assertion object for byte values
 */
fun Byte?.assert(): ByteAssert = ByteAssert(this)

/**
 * Creates a fluent assertion for Short values.
 *
 * This extension function provides access to AssertJ's ShortAssert methods for fluent testing
 * of short values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val value: Short = 1000
 * value.assert().isEqualTo(1000)
 * ```
 *
 * @receiver Short? The short value to assert on (nullable)
 * @return ShortAssert A fluent assertion object for short values
 */
fun Short?.assert(): ShortAssert = ShortAssert(this)

/**
 * Creates a fluent assertion for Int values.
 *
 * This extension function provides access to AssertJ's IntegerAssert methods for fluent testing
 * of integer values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val age = 25
 * age.assert().isGreaterThan(18).isLessThan(100)
 * ```
 *
 * @receiver Int? The integer value to assert on (nullable)
 * @return IntegerAssert A fluent assertion object for integer values
 */
fun Int?.assert(): IntegerAssert = IntegerAssert(this)

/**
 * Creates a fluent assertion for Long values.
 *
 * This extension function provides access to AssertJ's LongAssert methods for fluent testing
 * of long values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val timestamp = System.currentTimeMillis()
 * timestamp.assert().isGreaterThan(0)
 * ```
 *
 * @receiver Long? The long value to assert on (nullable)
 * @return LongAssert A fluent assertion object for long values
 */
fun Long?.assert(): LongAssert = LongAssert(this)

/**
 * Creates a fluent assertion for Float values.
 *
 * This extension function provides access to AssertJ's FloatAssert methods for fluent testing
 * of float values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val pi = 3.14f
 * pi.assert().isEqualTo(3.14f)
 * ```
 *
 * @receiver Float? The float value to assert on (nullable)
 * @return FloatAssert A fluent assertion object for float values
 */
fun Float?.assert(): FloatAssert = FloatAssert(this)

/**
 * Creates a fluent assertion for Double values.
 *
 * This extension function provides access to AssertJ's DoubleAssert methods for fluent testing
 * of double values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val price = 19.99
 * price.assert().isEqualTo(19.99)
 * ```
 *
 * @receiver Double? The double value to assert on (nullable)
 * @return DoubleAssert A fluent assertion object for double values
 */
fun Double?.assert(): DoubleAssert = DoubleAssert(this)

/**
 * Creates a fluent assertion for Char values.
 *
 * This extension function provides access to AssertJ's CharacterAssert methods for fluent testing
 * of character values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val grade = 'A'
 * grade.assert().isUpperCase().isEqualTo('A')
 * ```
 *
 * @receiver Char? The character value to assert on (nullable)
 * @return CharacterAssert A fluent assertion object for character values
 */
fun Char?.assert(): CharacterAssert = CharacterAssert(this)

/**
 * Creates a fluent assertion for BigDecimal values.
 *
 * This extension function provides access to AssertJ's BigDecimalAssert methods for fluent testing
 * of BigDecimal values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val amount = BigDecimal("123.45")
 * amount.assert().isEqualTo("123.45")
 * ```
 *
 * @receiver BigDecimal? The BigDecimal value to assert on (nullable)
 * @return BigDecimalAssert A fluent assertion object for BigDecimal values
 */
fun BigDecimal?.assert(): BigDecimalAssert = BigDecimalAssert(this)

/**
 * Creates a fluent assertion for BigInteger values.
 *
 * This extension function provides access to AssertJ's BigIntegerAssert methods for fluent testing
 * of BigInteger values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val count = BigInteger("12345678901234567890")
 * count.assert().isPositive().isGreaterThan(BigInteger.TEN)
 * ```
 *
 * @receiver BigInteger? The BigInteger value to assert on (nullable)
 * @return BigIntegerAssert A fluent assertion object for BigInteger values
 */
fun BigInteger?.assert(): BigIntegerAssert = BigIntegerAssert(this)

/**
 * Creates a fluent assertion for String values.
 *
 * This extension function provides access to AssertJ's StringAssert methods for fluent testing
 * of string values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val name = "FluentAssert"
 * name.assert().startsWith("Fluent").endsWith("Assert").contains("uentAss")
 * ```
 *
 * @receiver String? The string value to assert on (nullable)
 * @return StringAssert A fluent assertion object for string values
 */
fun String?.assert(): StringAssert = StringAssert(this)

/**
 * Creates a fluent assertion for StringBuilder values.
 *
 * [StringBuilder] is `Comparable<StringBuilder>`, so without this overload it would resolve to
 * [GenericComparableAssert] and lose the text assertions.
 *
 * Example:
 * ```kotlin
 * val text = StringBuilder("Fluent").append("Assert")
 * text.assert().startsWith("Fluent").hasSize(12)
 * ```
 *
 * @receiver StringBuilder? The string builder to assert on (nullable)
 * @return CharSequenceAssert A fluent assertion object for character sequences
 */
fun StringBuilder?.assert(): CharSequenceAssert = CharSequenceAssert(this)

/**
 * Creates a fluent assertion for StringBuffer values.
 *
 * [StringBuffer] is `Comparable<StringBuffer>`, so without this overload it would resolve to
 * [GenericComparableAssert] and lose the text assertions.
 *
 * Example:
 * ```kotlin
 * val text = StringBuffer("Fluent").append("Assert")
 * text.assert().endsWith("Assert").hasSize(12)
 * ```
 *
 * @receiver StringBuffer? The string buffer to assert on (nullable)
 * @return CharSequenceAssert A fluent assertion object for character sequences
 */
fun StringBuffer?.assert(): CharSequenceAssert = CharSequenceAssert(this)

/**
 * Creates a fluent assertion for any object type.
 *
 * This extension function provides access to AssertJ's ObjectAssert methods for fluent testing
 * of any object, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val person = Person("John", 30)
 * person.assert().isNotNull().hasFieldOrPropertyWithValue("name", "John")
 * ```
 *
 * @param T The type of the object being asserted
 * @receiver T? The object to assert on (nullable)
 * @return ObjectAssert<T> A fluent assertion object for the given type
 */
fun <T> T?.assert(): ObjectAssert<T> = assertThat(this)

/**
 * Creates a fluent assertion for Iterable collections.
 *
 * This extension function provides access to AssertJ's IterableAssert methods for fluent testing
 * of iterable collections, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val numbers = listOf(1, 2, 3, 4, 5)
 * numbers.assert().hasSize(5).contains(3)
 * ```
 *
 * @param T The type of elements in the iterable
 * @receiver Iterable<T>? The iterable collection to assert on (nullable)
 * @return IterableAssert<T> A fluent assertion object for iterable collections
 */
fun <T> Iterable<T>?.assert(): IterableAssert<T> = assertThat(this)

/**
 * Creates a fluent assertion for Iterator objects.
 *
 * This extension function provides access to AssertJ's IteratorAssert methods for fluent testing
 * of iterators, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val iterator = listOf(1, 2, 3).iterator()
 * iterator.assert().hasNext()
 * ```
 *
 * @param T The type of elements in the iterator
 * @receiver Iterator<T>? The iterator to assert on (nullable)
 * @return IteratorAssert<T> A fluent assertion object for iterators
 */
fun <T> Iterator<T>?.assert(): IteratorAssert<T> = assertThat(this)

/**
 * Creates a fluent assertion for Collection objects.
 *
 * This extension function provides access to AssertJ's CollectionAssert methods for fluent testing
 * of collections, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val set = setOf("apple", "banana", "orange")
 * set.assert().hasSize(3).contains("apple")
 * ```
 *
 * @param T The type of elements in the collection
 * @receiver Collection<T>? The collection to assert on (nullable)
 * @return CollectionAssert<T> A fluent assertion object for collections
 */
fun <T> Collection<T>?.assert(): CollectionAssert<T> = CollectionAssert(this)

/**
 * Creates a fluent assertion for Array objects.
 *
 * This extension function provides access to AssertJ's ObjectArrayAssert methods for fluent testing
 * of arrays, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val array = arrayOf("a", "b", "c")
 * array.assert().hasSize(3).contains("b")
 * ```
 *
 * @param T The type of elements in the array
 * @receiver Array<T>? The array to assert on (nullable)
 * @return ObjectArrayAssert<T> A fluent assertion object for arrays
 */
fun <T> Array<T>?.assert(): ObjectArrayAssert<T> = assertThat(this)

/**
 * Creates a fluent assertion for BooleanArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's BooleanArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = booleanArrayOf(true, false)
 * values.assert().contains(true)
 * ```
 *
 * @receiver BooleanArray? The array to assert on (nullable)
 * @return BooleanArrayAssert A fluent assertion object for boolean arrays
 */
fun BooleanArray?.assert(): BooleanArrayAssert = BooleanArrayAssert(this)

/**
 * Creates a fluent assertion for ByteArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's ByteArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = byteArrayOf(1, 2, 3)
 * values.assert().hasSize(3).contains(2)
 * ```
 *
 * @receiver ByteArray? The array to assert on (nullable)
 * @return ByteArrayAssert A fluent assertion object for byte arrays
 */
fun ByteArray?.assert(): ByteArrayAssert = ByteArrayAssert(this)

/**
 * Creates a fluent assertion for ShortArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's ShortArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = shortArrayOf(1, 2, 3)
 * values.assert().hasSize(3).contains(2)
 * ```
 *
 * @receiver ShortArray? The array to assert on (nullable)
 * @return ShortArrayAssert A fluent assertion object for short arrays
 */
fun ShortArray?.assert(): ShortArrayAssert = ShortArrayAssert(this)

/**
 * Creates a fluent assertion for IntArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's IntArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = intArrayOf(1, 2, 3)
 * values.assert().hasSize(3).containsExactly(1, 2, 3)
 * ```
 *
 * @receiver IntArray? The array to assert on (nullable)
 * @return IntArrayAssert A fluent assertion object for int arrays
 */
fun IntArray?.assert(): IntArrayAssert = IntArrayAssert(this)

/**
 * Creates a fluent assertion for LongArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's LongArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = longArrayOf(1, 2, 3)
 * values.assert().hasSize(3).contains(2L)
 * ```
 *
 * @receiver LongArray? The array to assert on (nullable)
 * @return LongArrayAssert A fluent assertion object for long arrays
 */
fun LongArray?.assert(): LongArrayAssert = LongArrayAssert(this)

/**
 * Creates a fluent assertion for FloatArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's FloatArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = floatArrayOf(1f, 2f)
 * values.assert().hasSize(2).contains(2f)
 * ```
 *
 * @receiver FloatArray? The array to assert on (nullable)
 * @return FloatArrayAssert A fluent assertion object for float arrays
 */
fun FloatArray?.assert(): FloatArrayAssert = FloatArrayAssert(this)

/**
 * Creates a fluent assertion for DoubleArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's DoubleArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = doubleArrayOf(1.0, 2.0)
 * values.assert().hasSize(2).contains(2.0)
 * ```
 *
 * @receiver DoubleArray? The array to assert on (nullable)
 * @return DoubleArrayAssert A fluent assertion object for double arrays
 */
fun DoubleArray?.assert(): DoubleArrayAssert = DoubleArrayAssert(this)

/**
 * Creates a fluent assertion for CharArray values.
 *
 * Primitive arrays are not `Array<T>`; this overload provides AssertJ's CharArrayAssert methods,
 * including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val values = charArrayOf('a', 'b')
 * values.assert().containsExactly('a', 'b')
 * ```
 *
 * @receiver CharArray? The array to assert on (nullable)
 * @return CharArrayAssert A fluent assertion object for char arrays
 */
fun CharArray?.assert(): CharArrayAssert = CharArrayAssert(this)

/**
 * Creates a fluent assertion for List objects.
 *
 * This extension function provides access to AssertJ's ListAssert methods for fluent testing
 * of lists, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val items = listOf("apple", "banana", "orange")
 * items.assert().hasSize(3).contains("apple", "banana")
 * ```
 *
 * @param T The type of elements in the list
 * @receiver List<T>? The list to assert on (nullable)
 * @return ListAssert<T> A fluent assertion object for lists
 */
fun <T> List<T>?.assert(): ListAssert<T> = ListAssert(this)

/**
 * Creates a fluent assertion for Optional objects.
 *
 * This extension function provides access to AssertJ's OptionalAssert methods for fluent testing
 * of Optional values, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val present = Optional.of("value")
 * present.assert().isPresent().contains("value")
 *
 * val empty = Optional.empty<String>()
 * empty.assert().isEmpty()
 * ```
 *
 * @param T The type of the optional value
 * @receiver Optional<T>? The optional to assert on (nullable)
 * @return OptionalAssert<T> A fluent assertion object for optional values
 */
fun <T> Optional<T>?.assert(): OptionalAssert<T> = assertThat<T>(this)

/**
 * Creates a fluent assertion for Map objects.
 *
 * This extension function provides access to AssertJ's MapAssert methods for fluent testing
 * of maps, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val map = mapOf("key1" to "value1", "key2" to "value2")
 * map.assert().hasSize(2).containsKey("key1").containsValue("value1")
 * ```
 *
 * @param K The type of keys in the map
 * @param V The type of values in the map
 * @receiver Map<K, V>? The map to assert on (nullable)
 * @return MapAssert<K, V> A fluent assertion object for maps
 */
fun <K, V> Map<K, V>?.assert(): MapAssert<K, V> = MapAssert(this)

/**
 * Creates a fluent assertion for Stream objects.
 *
 * This extension function provides access to AssertJ's ListAssert methods for fluent testing
 * of streams by converting them to lists, including null-safe operations.
 *
 * Example:
 * ```kotlin
 * val stream = listOf(1, 2, 3, 4, 5).stream()
 * stream.assert().hasSize(5).contains(3)
 * ```
 *
 * @param T The type of elements in the stream
 * @receiver Stream<T>? The stream to assert on (nullable)
 * @return ListAssert<T> A fluent assertion object for streams (converted to list)
 */
fun <T> Stream<T>?.assert(): ListAssert<T> = assertThat(this)

/**
 * Creates a fluent assertion for Comparable objects.
 *
 * This extension function provides access to AssertJ's GenericComparableAssert methods for fluent testing
 * of comparable objects, enabling comparison operations. Types with more specific overloads (such as String)
 * resolve to their dedicated assertion class instead.
 *
 * Example:
 * ```kotlin
 * class Version(val value: Int) : Comparable<Version> {
 *     override fun compareTo(other: Version): Int = value.compareTo(other.value)
 * }
 *
 * Version(2).assert().isGreaterThan(Version(1)).isLessThan(Version(3))
 * ```
 *
 * @param T The comparable type (must implement Comparable<T>)
 * @receiver T? The comparable object to assert on (nullable)
 * @return GenericComparableAssert<T> A fluent assertion object for comparable objects
 */
@Suppress("UNCHECKED_CAST")
fun <T : Comparable<T>?> T?.assert(): GenericComparableAssert<T> = GenericComparableAssert(this as T)
