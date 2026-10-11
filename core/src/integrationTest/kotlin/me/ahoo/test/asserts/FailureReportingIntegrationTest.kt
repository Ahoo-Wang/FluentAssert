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
import java.time.LocalDate
import java.time.Month

/**
 * Verifies that failures raised through FluentAssert carry AssertJ's own diagnostics, so switching from
 * `assertThat(x)` to `x.assert()` never degrades failure messages.
 */
class FailureReportingIntegrationTest {

    @Test
    fun `given unequal strings when assert then AssertJ reports expected and actual`() {
        assertThrownBy<AssertionError> {
            "actual-value".assert().isEqualTo("expected-value")
        }.hasMessageContaining("actual-value").hasMessageContaining("expected-value")
    }

    @Test
    fun `given null receiver when assert not null then AssertJ reports null actual`() {
        val missing: String? = null

        assertThrownBy<AssertionError> {
            missing.assert().isNotNull()
        }.hasMessageContaining("Expecting actual not to be null")
    }

    @Test
    fun `given primitive array when assert contains then AssertJ reports missing element`() {
        assertThrownBy<AssertionError> {
            intArrayOf(1, 2, 3).assert().contains(42)
        }.hasMessageContaining("42")
    }

    @Test
    fun `given described assertion when it fails then description is reported`() {
        assertThrownBy<AssertionError> {
            LocalDate.of(2026, Month.APRIL, 1).assert().describedAs("release date").hasMonth(Month.MAY)
        }.hasMessageContaining("release date")
    }

    @Test
    fun `given domain assert provider when assert then provider assertion is used`() {
        val customer = Customer("Ada")

        customer.assert().isEqualTo("Ada")
        assertThrownBy<AssertionError> {
            customer.assert().isEqualTo("Grace")
        }.hasMessageContaining("Grace")
    }

    private class Customer(private val name: String) : AssertProvider<StringAssert> {
        override fun assertThat(): StringAssert = name.assert()
    }
}
