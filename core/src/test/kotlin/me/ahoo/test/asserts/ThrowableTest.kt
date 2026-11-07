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

import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.presentation.StandardRepresentation
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.IOException

class ThrowableTest {

    @Test
    fun `given Throwable when assert then ThrowableAssert`() {
        Throwable("1").assert().assert().isInstanceOf(ThrowableAssert::class.java)
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `given Throwable Function when assertThrownBy then ThrowableAssert`() {
        val assertion = assertThrownBy<Throwable> {
            throw Throwable("1")
        }
        assertInstanceOf(ThrowableAssert::class.java, assertion)
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `given no thrown exception when assertThrownBy then throw AssertionError`() {
        assertThrownBy<AssertionError> {
            assertThrownBy<Throwable> {
                null
            }
        }.hasMessageContaining("Expected Throwable to be thrown, but was: null")
    }

    @Test
    fun `given wrong thrown exception type when assertThrownBy then throw AssertionError`() {
        assertThrownBy<AssertionError> {
            assertThrownBy<IllegalArgumentException> {
                throw IllegalStateException("wrong")
            }
        }.hasMessageContaining(IllegalArgumentException::class.java.name)
    }

    @Test
    fun `given matching type when chained message assertion fails then failure reports message not type`() {
        val error = assertThrows(AssertionError::class.java) {
            assertThrownBy<IllegalArgumentException> {
                throw IllegalArgumentException("boom-expected")
            }.hasMessageContaining("wrong-fragment")
        }
        error.message!!.assert()
            .contains("wrong-fragment")
            .contains("boom-expected")
        error.message!!.assert().doesNotContain("to be an instance of")
    }

    @Test
    fun `given matching type and message when assertThrownBy then chained assertions pass`() {
        assertThrownBy<IllegalArgumentException> {
            throw IllegalArgumentException("boom-expected")
        }.hasMessage("boom-expected").hasMessageContaining("boom")
    }

    @Test
    fun `given matching type when assertThrownBy then the throwable is never rendered`() {
        val expected = UnrenderableException()

        assertThrownBy<UnrenderableException> { throw expected }.isSameAs(expected)
        assertThrownBy(UnrenderableException::class.java) { throw expected }.isSameAs(expected)

        expected.renderings.assert().isZero()
    }

    @Test
    fun `given matching type when assertThrownBy then only user descriptions are emitted`() {
        val descriptions = mutableListOf<String>()
        Assertions.setDescriptionConsumer { description ->
            description.value().takeIf { it.isNotEmpty() }?.let(descriptions::add)
        }
        try {
            assertThrownBy<IOException> { throw IOException("detail") }
                .describedAs("user description")
                .hasMessage("detail")
        } finally {
            Assertions.setDescriptionConsumer(null)
        }
        descriptions.assert().containsExactly("user description")
    }

    @Test
    fun `given wrong type when assertThrownBy then failure uses the configured representation`() {
        Assertions.useRepresentation(object : StandardRepresentation() {
            override fun toStringOf(value: Any?): String? =
                if (value is Throwable) "CUSTOM_THROWABLE" else super.toStringOf(value)
        })
        try {
            val reified = assertThrows(AssertionError::class.java) {
                assertThrownBy<IllegalArgumentException> { throw IllegalStateException("actual") }
            }
            val byClass = assertThrows(AssertionError::class.java) {
                assertThrownBy(IllegalArgumentException::class.java) { throw IllegalStateException("actual") }
            }
            val description = "Expected IllegalArgumentException to be thrown, but was: java.lang.IllegalStateException"
            listOf(reified, byClass).forEach {
                it.message.assert().contains("CUSTOM_THROWABLE").contains(description)
            }
        } finally {
            Assertions.useDefaultRepresentation()
        }
    }

    private class UnrenderableException : RuntimeException("expected") {
        var renderings = 0

        override fun toString(): String {
            renderings++
            error("render failed")
        }
    }
}
