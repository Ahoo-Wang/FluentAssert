package me.ahoo.test.asserts

import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Test

class ThrowableTest {

    @Test
    fun `given Throwable when assert then ThrowableAssert`() {
        Throwable("1").assert().assert().isInstanceOf(ThrowableAssert::class.java)
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `given Throwable Function when assertThrownBy then ThrowableAssert`() {
        assertThrownBy<Throwable> {
            throw Throwable("1")
        }.assert().isInstanceOf(ThrowableAssert::class.java)
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `given Null when assertThrownBy then throw AssertionError`() {
        assertThrownBy<AssertionError> {
            assertThrownBy<Throwable> {
                null
            }.assert()
        }.assert().withFailMessage { "Expected Throwable to be thrown, but was: null" }
    }
}
