package me.ahoo.test.asserts

import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.error.ShouldBeInstance.shouldBeInstance

/**
 * Asserts that a specific type of exception is thrown by the given executable (reified version).
 *
 * This inline function provides a more convenient syntax for asserting exceptions using Kotlin's
 * reified generics, eliminating the need to explicitly specify the exception class.
 *
 * Example:
 * ```kotlin
 * assertThrownBy<IllegalArgumentException> {
 *     throw IllegalArgumentException("invalid argument")
 * }.assert().hasMessage("invalid argument")
 * ```
 *
 * @param T The type of exception expected to be thrown (must extend Throwable)
 * @param shouldRaiseThrowable The executable that should throw the exception
 * @return ThrowableAssert<T> A fluent assertion object for the thrown exception
 * @throws AssertionError if no exception is thrown or if the wrong type of exception is thrown
 */
fun <T : Throwable> T?.assert(): ThrowableAssert<T> = ThrowableAssert(this)

/**
 * Asserts that a specific type of exception is thrown by the given executable.
 *
 * This function executes the provided lambda and verifies that it throws an exception of the specified type.
 * It provides detailed error messages when the assertion fails.
 *
 * Example:
 * ```kotlin
 * assertThrownBy(IllegalArgumentException::class.java) {
 *     throw IllegalArgumentException("invalid argument")
 * }.assert().hasMessage("invalid argument")
 * ```
 *
 * @param T The type of exception expected to be thrown (must extend Throwable)
 * @param throwableType The class of the expected exception
 * @param shouldRaiseThrowable The executable that should throw the exception
 * @return ThrowableAssert<T> A fluent assertion object for the thrown exception
 * @throws AssertionError if no exception is thrown or if the wrong type of exception is thrown
 */
@Suppress("UNCHECKED_CAST")
fun <T : Throwable> assertThrownBy(
    throwableType: Class<T>,
    shouldRaiseThrowable: () -> Unit
): ThrowableAssert<T> {
    val throwable = Assertions.catchThrowable(shouldRaiseThrowable)
    return throwable
        .assert()
        .describedAs { "Expected ${throwableType.simpleName} to be thrown, but was: $throwable" }
        .isNotNull()
        .overridingErrorMessage(shouldBeInstance(throwable, throwableType).create())
        .isInstanceOf(throwableType) as ThrowableAssert<T>
}

/**
 * Asserts that a specific type of exception is thrown by the given executable (reified version).
 *
 * This inline function provides a more convenient syntax for asserting exceptions using Kotlin's
 * reified generics, eliminating the need to explicitly specify the exception class.
 *
 * Example:
 * ```kotlin
 * assertThrownBy<IllegalArgumentException> {
 *     throw IllegalArgumentException("invalid argument")
 * }.assert().hasMessage("invalid argument")
 * ```
 *
 * @param T The type of exception expected to be thrown (must extend Throwable)
 * @param shouldRaiseThrowable The executable that should throw the exception
 * @return ThrowableAssert<T> A fluent assertion object for the thrown exception
 * @throws AssertionError if no exception is thrown or if the wrong type of exception is thrown
 */
inline fun <reified T : Throwable> assertThrownBy(noinline shouldRaiseThrowable: () -> Unit): ThrowableAssert<T> =
    assertThrownBy(T::class.java, shouldRaiseThrowable)
