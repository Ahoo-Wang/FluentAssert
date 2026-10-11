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

package me.ahoo.test.asserts.compatibility

import me.ahoo.test.asserts.assert
import me.ahoo.test.asserts.assertThrownBy
import org.assertj.core.api.AssertProvider
import org.assertj.core.api.CharSequenceAssert
import org.assertj.core.api.CharacterAssert
import org.assertj.core.api.CompletableFutureAssert
import org.assertj.core.api.FutureAssert
import org.assertj.core.api.GenericComparableAssert
import org.assertj.core.api.IntArrayAssert
import org.assertj.core.api.IterableAssert
import org.assertj.core.api.ListAssert
import org.assertj.core.api.StringAssert
import org.assertj.core.api.TemporalAssert
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.Temporal
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.ScheduledFuture

/**
 * Compiled by the consumer's Kotlin compiler against the published POMs. Typed assignments check that overload
 * resolution is the same as with the build toolchain; [assertThrownBy] checks the inline function body.
 */
class KotlinConsumerTest {

    data class Version(val value: Int) : Comparable<Version> {
        override fun compareTo(other: Version): Int = value.compareTo(other.value)
    }

    @Test
    fun `overloads resolve as with the build toolchain`() {
        val string: StringAssert = (null as String?).assert()
        val char: CharacterAssert = 'a'.assert()
        val builder: CharSequenceAssert = StringBuilder("ab").assert()
        val ints: IntArrayAssert = intArrayOf(1, 2).assert()
        val list: ListAssert<Int> = listOf(1, 2).assert()
        val iterable: IterableAssert<Int> = (listOf(1, 2) as Iterable<Int>).assert()
        val temporal: TemporalAssert = (Instant.EPOCH as Temporal).assert()
        val comparable: GenericComparableAssert<Version> = Version(2).assert()
        val scheduled: FutureAssert<String> = (null as ScheduledFuture<String>?).assert()
        val stage: CompletableFutureAssert<String> = (CompletableFuture.completedFuture("done") as CompletionStage<String>).assert()

        string.isNull()
        char.isLowerCase()
        builder.startsWith("a")
        ints.containsExactly(1, 2)
        list.hasSize(2)
        iterable.contains(1)
        temporal.isEqualTo(Instant.EPOCH)
        comparable.isGreaterThan(Version(1))
        scheduled.isNull()
        stage.isCompletedWithValue("done")
    }

    @Test
    fun `inline reified assertThrownBy works when inlined by the consumer compiler`() {
        assertThrownBy<IllegalArgumentException> {
            require(false) { "invalid" }
        }.hasMessage("invalid")
    }

    @Test
    fun `assert provider returns the provided assertion`() {
        val provider = AssertProvider<StringAssert> { "provided".assert() }
        provider.assert().isEqualTo("provided")
    }
}
