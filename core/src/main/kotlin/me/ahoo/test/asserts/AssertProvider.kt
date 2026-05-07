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
import org.assertj.core.api.Assertions.assertThat

/**
 * Creates a fluent assertion for [AssertProvider] objects.
 *
 * Delegates to AssertJ's [assertThat], which internally calls [AssertProvider.assertThat]
 * to obtain the subject of the assertion.
 *
 * @param A The assertion type provided by this provider
 * @receiver AssertProvider<A> The assert provider to assert on
 * @return A The fluent assertion for the provider's actual value
 */
fun <A> AssertProvider<A>.assert(): A = assertThat(this)
