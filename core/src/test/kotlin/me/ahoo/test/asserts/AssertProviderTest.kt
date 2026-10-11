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

import org.assertj.core.api.AssertProvider
import org.assertj.core.api.StringAssert
import org.junit.jupiter.api.Test

class AssertProviderTest {

    class StringAssertProvider(private val value: String) : AssertProvider<StringAssert> {
        override fun assertThat(): StringAssert {
            return value.assert()
        }
    }

    @Test
    fun `given StringAssertProvider when assert then StringAssert`() {
        val provider = StringAssertProvider("hello")
        provider.assert().isEqualTo("hello")
    }

    @Test
    fun `given AssertProvider when assert then isInstanceOf`() {
        val provider = StringAssertProvider("test")
        provider.assert().assert().isInstanceOf(StringAssert::class.java)
    }
}
