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
 * Returns a [BooleanAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.boolean
 */
fun Boolean?.assert(): BooleanAssert = BooleanAssert(this)

/**
 * Returns a [ByteAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.byte
 */
fun Byte?.assert(): ByteAssert = ByteAssert(this)

/**
 * Returns a [ShortAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.short
 */
fun Short?.assert(): ShortAssert = ShortAssert(this)

/**
 * Returns an [IntegerAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.int
 */
fun Int?.assert(): IntegerAssert = IntegerAssert(this)

/**
 * Returns a [LongAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.long
 */
fun Long?.assert(): LongAssert = LongAssert(this)

/**
 * Returns a [FloatAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.float
 */
fun Float?.assert(): FloatAssert = FloatAssert(this)

/**
 * Returns a [DoubleAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.double
 */
fun Double?.assert(): DoubleAssert = DoubleAssert(this)

/**
 * Returns a [CharacterAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.char
 */
fun Char?.assert(): CharacterAssert = CharacterAssert(this)

/**
 * Returns a [BigDecimalAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.bigDecimal
 */
fun BigDecimal?.assert(): BigDecimalAssert = BigDecimalAssert(this)

/**
 * Returns a [BigIntegerAssert] for this value.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.bigInteger
 */
fun BigInteger?.assert(): BigIntegerAssert = BigIntegerAssert(this)

/**
 * Returns a [StringAssert] for this value.
 *
 * Takes precedence over the `Comparable` overload even though [String] is comparable.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.string
 */
fun String?.assert(): StringAssert = StringAssert(this)

/**
 * Returns a [CharSequenceAssert] for this builder.
 *
 * [StringBuilder] is `Comparable<StringBuilder>`; this dedicated overload keeps text assertions instead of [GenericComparableAssert].
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.stringBuilder
 */
fun StringBuilder?.assert(): CharSequenceAssert = CharSequenceAssert(this)

/**
 * Returns a [CharSequenceAssert] for this buffer.
 *
 * [StringBuffer] is `Comparable<StringBuffer>`; this dedicated overload keeps text assertions instead of [GenericComparableAssert].
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.stringBuffer
 */
fun StringBuffer?.assert(): CharSequenceAssert = CharSequenceAssert(this)

/**
 * Returns an [ObjectAssert] for this value.
 *
 * Fallback for receivers whose static type has no more specific overload.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.anyObject
 */
fun <T> T?.assert(): ObjectAssert<T> = assertThat(this)

/**
 * Returns an [IterableAssert] for this iterable.
 *
 * Only used when the static type is [Iterable]; [Collection] and [List] receivers resolve to their own overloads.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.iterable
 */
fun <T> Iterable<T>?.assert(): IterableAssert<T> = assertThat(this)

/**
 * Returns an [IteratorAssert] for this iterator.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.iterator
 */
fun <T> Iterator<T>?.assert(): IteratorAssert<T> = assertThat(this)

/**
 * Returns a [CollectionAssert] for this collection (for example a [Set]); [List] receivers resolve to [ListAssert].
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.collection
 */
fun <T> Collection<T>?.assert(): CollectionAssert<T> = CollectionAssert(this)

/**
 * Returns an [ObjectArrayAssert] for this array.
 *
 * Primitive arrays such as [IntArray] are not `Array<T>` and have their own overloads.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.array
 */
fun <T> Array<T>?.assert(): ObjectArrayAssert<T> = assertThat(this)

/**
 * Returns a [BooleanArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun BooleanArray?.assert(): BooleanArrayAssert = BooleanArrayAssert(this)

/**
 * Returns a [ByteArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun ByteArray?.assert(): ByteArrayAssert = ByteArrayAssert(this)

/**
 * Returns a [ShortArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun ShortArray?.assert(): ShortArrayAssert = ShortArrayAssert(this)

/**
 * Returns an [IntArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun IntArray?.assert(): IntArrayAssert = IntArrayAssert(this)

/**
 * Returns a [LongArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun LongArray?.assert(): LongArrayAssert = LongArrayAssert(this)

/**
 * Returns a [FloatArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun FloatArray?.assert(): FloatArrayAssert = FloatArrayAssert(this)

/**
 * Returns a [DoubleArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun DoubleArray?.assert(): DoubleArrayAssert = DoubleArrayAssert(this)

/**
 * Returns a [CharArrayAssert] for this array.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.primitiveArrays
 */
fun CharArray?.assert(): CharArrayAssert = CharArrayAssert(this)

/**
 * Returns a [ListAssert] for this list.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.list
 */
fun <T> List<T>?.assert(): ListAssert<T> = ListAssert(this)

/**
 * Returns an [OptionalAssert] for this optional.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.optional
 */
fun <T> Optional<T>?.assert(): OptionalAssert<T> = assertThat<T>(this)

/**
 * Returns a [MapAssert] for this map.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.map
 */
fun <K, V> Map<K, V>?.assert(): MapAssert<K, V> = MapAssert(this)

/**
 * Returns a [ListAssert] over the elements of this stream.
 *
 * The stream is consumed when the assertion is created.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.stream
 */
fun <T> Stream<T>?.assert(): ListAssert<T> = assertThat(this)

/**
 * Returns a [GenericComparableAssert] for this comparable value.
 *
 * Applies to comparable types without a more specific overload, such as user value types. A type that is also [Iterable], [Map], [java.util.concurrent.Future] or [Throwable] makes the call ambiguous; declare the receiver with the intended static type instead.
 *
 * @sample me.ahoo.test.asserts.samples.JdkSamples.comparable
 */
@Suppress("UNCHECKED_CAST")
fun <T : Comparable<T>?> T?.assert(): GenericComparableAssert<T> = GenericComparableAssert(this as T)
