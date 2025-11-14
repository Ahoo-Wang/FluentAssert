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
import org.assertj.core.api.PredicateAssert
import java.util.function.Predicate

/**
 * Creates a fluent assertion for Predicate objects.
 *
 * This extension function provides access to AssertJ's PredicateAssert methods for fluent testing
 * of Predicate functions, including null-safe operations and predicate behavior verification.
 *
 * Example:
 * ```kotlin
 * val isEven = Predicate<Int> { it % 2 == 0 }
 * isEven.assert().accepts(2, 4).rejects(1, 3)
 * ```
 *
 * @param T The type of input the predicate accepts
 * @receiver Predicate<T>? The predicate to assert on (nullable)
 * @return PredicateAssert<T> A fluent assertion object for predicates
 */
fun <T> Predicate<T>?.assert(): PredicateAssert<T> = assertThat(this)
