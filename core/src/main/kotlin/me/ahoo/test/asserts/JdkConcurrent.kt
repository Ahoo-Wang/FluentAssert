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
import org.assertj.core.api.CompletableFutureAssert
import org.assertj.core.api.FutureAssert
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Future

/**
 * Creates a fluent assertion for Future objects.
 *
 * This extension function provides access to AssertJ's FutureAssert methods for fluent testing
 * of Future objects, including null-safe operations and asynchronous result verification.
 *
 * Example:
 * ```kotlin
 * val future = executor.submit(Callable { "result" })
 * future.assert().isDone().isNotCancelled()
 * ```
 *
 * @param V The type of the future's result value
 * @receiver Future<V>? The future to assert on (nullable)
 * @return FutureAssert<V> A fluent assertion object for futures
 */
fun <V> Future<V>?.assert(): FutureAssert<V> = assertThat(this)

/**
 * Creates a fluent assertion for CompletableFuture objects.
 *
 * This extension function provides access to AssertJ's CompletableFutureAssert methods for fluent testing
 * of CompletableFuture objects, including null-safe operations and completion state verification.
 *
 * Example:
 * ```kotlin
 * val future = CompletableFuture.completedFuture("success")
 * future.assert().isCompleted().isCompletedWithValue("success")
 * ```
 *
 * @param V The type of the completable future's result value
 * @receiver CompletableFuture<V>? The completable future to assert on (nullable)
 * @return CompletableFutureAssert<V> A fluent assertion object for completable futures
 */
fun <V> CompletableFuture<V>?.assert(): CompletableFutureAssert<V> = assertThat(this)

/**
 * Creates a fluent assertion for CompletionStage objects.
 *
 * This extension function provides access to AssertJ's CompletableFutureAssert methods for fluent testing
 * of CompletionStage objects, including null-safe operations and completion state verification.
 *
 * Example:
 * ```kotlin
 * val stage = CompletableFuture.completedFuture("result")
 * stage.assert().isCompleted().isCompletedWithValue("result")
 * ```
 *
 * @param V The type of the completion stage's result value
 * @receiver CompletionStage<V>? The completion stage to assert on (nullable)
 * @return CompletableFutureAssert<V> A fluent assertion object for completion stages
 */
fun <V> CompletionStage<V>?.assert(): CompletableFutureAssert<V> = assertThat(this)
