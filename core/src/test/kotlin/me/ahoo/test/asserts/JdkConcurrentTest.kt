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

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.CompletableFutureAssert
import org.assertj.core.api.FutureAssert
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Future
import java.util.concurrent.ScheduledFuture

class JdkConcurrentTest {

    @Test
    fun `given Future when assert then FutureAssert`() {
        val value = mockk<Future<String>>()
        value.assert().assert().isInstanceOf(FutureAssert::class.java)
    }

    @Test
    fun `given CompletableFuture when assert then CompletableFutureAssert`() {
        val value = CompletableFuture.completedFuture("test")
        value.assert().isCompletedWithValue("test")
        value.assert().assert().isInstanceOf(CompletableFutureAssert::class.java)
    }

    @Test
    fun `given CompletionStage when assert then CompletableFutureAssert`() {
        val value = mockk<CompletionStage<String>> {
            every {
                toCompletableFuture()
            } returns mockk()
        }
        value.assert().assert().isInstanceOf(CompletableFutureAssert::class.java)
    }

    @Test
    fun `given ScheduledFuture when assert then FutureAssert`() {
        val value = mockk<ScheduledFuture<String>>()
        val assertion: FutureAssert<String> = value.assert()
        assertion.assert().isInstanceOf(FutureAssert::class.java)
    }
}
