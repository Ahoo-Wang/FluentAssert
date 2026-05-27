package me.ahoo.test.asserts

import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test

class ThrowableTest {

    @Test
    fun `given Throwable when assert then ThrowableAssert`() {
        Throwable("1").assert().assert().isInstanceOf(ThrowableAssert::class.java)
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `given Throwable Function when assertThrownBy then ThrowableAssert`() {
        val assertion = assertThrownBy<Throwable> {
            throw Throwable("1")
        }
        assertInstanceOf(ThrowableAssert::class.java, assertion)
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `given no thrown exception when assertThrownBy then throw AssertionError`() {
        assertThrownBy<AssertionError> {
            assertThrownBy<Throwable> {
                null
            }
        }.hasMessageContaining("Expected Throwable to be thrown, but was: null")
    }

    @Test
    fun `given wrong thrown exception type when assertThrownBy then throw AssertionError`() {
        assertThrownBy<AssertionError> {
            assertThrownBy<IllegalArgumentException> {
                throw IllegalStateException("wrong")
            }
        }.hasMessageContaining(IllegalArgumentException::class.java.name)
    }
}
