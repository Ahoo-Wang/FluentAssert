package me.ahoo.test.asserts

import io.mockk.mockk
import org.assertj.core.api.PredicateAssert
import org.junit.jupiter.api.Test
import java.util.function.Predicate

class JdkFunctionTest {
    @Test
    fun `given Predicate when assert then PredicateAssert`() {
        val value = mockk<Predicate<String>>()
        value.assert().assert().isInstanceOf(PredicateAssert::class.java)
    }
}
