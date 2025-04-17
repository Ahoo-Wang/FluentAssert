package me.ahoo.test.asserts

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.PredicateAssert
import org.junit.jupiter.api.Test
import java.util.function.Predicate

class JdkFunctionTest {
    @Test
    fun `given Predicate when assert then PredicateAssert`() {
        val value = mockk<Predicate<String>>()
        assertThat(value.assert()).isInstanceOf(PredicateAssert::class.java)
    }
}
