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

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ConcurrencyIntegrationTest {

    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    @AfterEach
    fun shutdown() {
        scheduler.shutdownNow()
    }

    @Test
    fun `given scheduled task when assert then future succeeds within timeout`() {
        val future = scheduler.schedule(Callable { "scheduled" }, 10, TimeUnit.MILLISECONDS)

        future.assert().succeedsWithin(Duration.ofSeconds(5)).isEqualTo("scheduled")
        future.assert().isDone().isNotCancelled()
    }

    @Test
    fun `given submitted task when assert then plain future succeeds`() {
        val future = scheduler.submit(Callable { 42 })

        future.assert().succeedsWithin(Duration.ofSeconds(5)).isEqualTo(42)
    }

    @Test
    fun `given async completable future when assert then completes with value`() {
        val future = CompletableFuture.supplyAsync({ "async" }, scheduler)

        future.assert().succeedsWithin(Duration.ofSeconds(5)).isEqualTo("async")
        future.assert().isCompletedWithValue("async")
    }

    @Test
    fun `given failing completion stage when assert then fails with cause`() {
        val stage = CompletableFuture.supplyAsync({ error("boom") }, scheduler).thenApply { it }

        stage.assert()
            .failsWithin(Duration.ofSeconds(5))
            .withThrowableOfType(ExecutionException::class.java)
            .withCauseInstanceOf(IllegalStateException::class.java)
            .withMessageContaining("boom")
    }
}
