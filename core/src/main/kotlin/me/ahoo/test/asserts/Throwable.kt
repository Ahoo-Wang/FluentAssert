package me.ahoo.test.asserts

import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.error.ShouldBeInstance.shouldBeInstance

fun <T : Throwable> T?.assert(): ThrowableAssert<T> {
    return ThrowableAssert(this)
}

@Suppress("UNCHECKED_CAST")
fun <T : Throwable> assertThrownBy(throwableType: Class<T>, shouldRaiseThrowable: () -> Unit): ThrowableAssert<T> {
    val throwable = Assertions.catchThrowable(shouldRaiseThrowable)
    return throwable.assert()
        .describedAs { "Expected ${throwableType.simpleName} to be thrown, but was: $throwable" }
        .isNotNull()
        .overridingErrorMessage(shouldBeInstance(throwable, throwableType).create())
        .isInstanceOf(throwableType) as ThrowableAssert<T>
}

inline fun <reified T : Throwable> assertThrownBy(noinline shouldRaiseThrowable: () -> Unit): ThrowableAssert<T> {
    return assertThrownBy(T::class.java, shouldRaiseThrowable)
}
