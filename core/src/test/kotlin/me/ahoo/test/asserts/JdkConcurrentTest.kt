package me.ahoo.test.asserts

import io.mockk.mockk
import org.assertj.core.api.FutureAssert
import org.junit.jupiter.api.Test
import java.util.concurrent.Future

class JdkConcurrentTest {

    @Test
    fun `given Future when assert then FutureAssert`() {
        val value = mockk<Future<String>>()
        value.assert().assert().isInstanceOf(FutureAssert::class.java)
    }
}
