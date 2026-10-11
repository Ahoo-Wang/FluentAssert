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

import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

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
}
