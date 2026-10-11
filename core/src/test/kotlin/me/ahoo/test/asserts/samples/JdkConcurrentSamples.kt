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

package me.ahoo.test.asserts.samples

import me.ahoo.test.asserts.assert
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Executable samples rendered into the KDoc of `JdkConcurrent.kt` via `@sample`.
 */
class JdkConcurrentSamples {

    @Test
    fun future() {
        val executor = Executors.newSingleThreadExecutor()
        val future = executor.submit(Callable { "result" })
        future.assert().succeedsWithin(Duration.ofSeconds(5)).isEqualTo("result")
        executor.shutdown()
    }

    @Test
    fun scheduledFuture() {
        val scheduler = Executors.newSingleThreadScheduledExecutor()
        val future = scheduler.schedule(Callable { "result" }, 10, TimeUnit.MILLISECONDS)
        future.assert().succeedsWithin(Duration.ofSeconds(5)).isEqualTo("result")
        scheduler.shutdown()
    }

    @Test
    fun completableFuture() {
        val future = CompletableFuture.completedFuture("success")
        future.assert().isCompletedWithValue("success")
    }

    @Test
    fun completionStage() {
        val stage: CompletionStage<String> = CompletableFuture.completedFuture("result")
        stage.assert().isCompletedWithValue("result")
    }
}
