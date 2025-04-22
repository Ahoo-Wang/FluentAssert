package me.ahoo.test.asserts

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.CompletableFutureAssert
import org.assertj.core.api.FutureAssert
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Future

class JdkConcurrentTest {

    @Test
    fun `given Future when assert then FutureAssert`() {
        val value = mockk<Future<String>>()
        value.assert().assert().isInstanceOf(FutureAssert::class.java)
    }

    @Test
    fun `given CompletableFuture when assert then CompletableFutureAssert`() {
        val value = mockk<CompletableFuture<String>>()
        value.assert().assert().isInstanceOf(CompletableFutureAssert::class.java)
    }

    @Test
    fun `given CompletionStage when assert then CompletableFutureAssert`() {
        val value = mockk<CompletionStage<String>> {
            every {
                toCompletableFuture()
            } returns mockk()
        }
        value.assert().assert().isInstanceOf(CompletableFutureAssert::class.java)
    }
}
