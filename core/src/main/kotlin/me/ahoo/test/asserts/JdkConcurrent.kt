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

fun <V> Future<V>?.assert(): FutureAssert<V> {
    return assertThat(this)
}

fun <V> CompletableFuture<V>?.assert(): CompletableFutureAssert<V> {
    return assertThat(this)
}

fun <V> CompletionStage<V>?.assert(): CompletableFutureAssert<V> {
    return assertThat(this)
}
