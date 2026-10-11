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

import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.error.ShouldBeInstance.shouldBeInstance

/**
 * Returns a [ThrowableAssert] for this throwable.
 *
 * @sample me.ahoo.test.asserts.samples.ThrowableSamples.throwable
 */
fun <T : Throwable> T?.assert(): ThrowableAssert<T> = ThrowableAssert(this)

/**
 * Runs [shouldRaiseThrowable] and returns a [ThrowableAssert] for the throwable it raised.
 *
 * Use this overload when the expected type is only available as a [Class]; otherwise prefer the reified one.
 * The returned assertion is fresh, so failures of chained assertions report their own diagnostics.
 *
 * @param throwableType the expected type; subtypes are accepted
 * @param shouldRaiseThrowable the code that should throw
 * @throws AssertionError if nothing is thrown or the throwable is not a [throwableType]
 * @sample me.ahoo.test.asserts.samples.ThrowableSamples.assertThrownByClass
 */
@Suppress("UNCHECKED_CAST")
fun <T : Throwable> assertThrownBy(
    throwableType: Class<T>,
    shouldRaiseThrowable: () -> Unit
): ThrowableAssert<T> {
    val throwable = Assertions.catchThrowable(shouldRaiseThrowable)
    // describedAs/overridingErrorMessage persist on the assert instance's WritableAssertionInfo,
    // so run the diagnostics on a throwaway instance and return a fresh one — otherwise chained
    // assertions report their failures as type-check errors.
    throwable
        .assert()
        .describedAs { "Expected ${throwableType.simpleName} to be thrown, but was: $throwable" }
        .isNotNull()
        .overridingErrorMessage(shouldBeInstance(throwable, throwableType).create())
        .isInstanceOf(throwableType)
    return throwable.assert() as ThrowableAssert<T>
}

/**
 * Runs [shouldRaiseThrowable] and returns a [ThrowableAssert] for the throwable it raised, expecting a [T].
 *
 * @throws AssertionError if nothing is thrown or the throwable is not a [T]
 * @sample me.ahoo.test.asserts.samples.ThrowableSamples.assertThrownByReified
 */
inline fun <reified T : Throwable> assertThrownBy(noinline shouldRaiseThrowable: () -> Unit): ThrowableAssert<T> =
    assertThrownBy(T::class.java, shouldRaiseThrowable)
