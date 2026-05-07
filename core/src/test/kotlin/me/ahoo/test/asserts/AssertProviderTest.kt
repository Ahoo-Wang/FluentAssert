package me.ahoo.test.asserts

import org.assertj.core.api.AssertProvider
import org.assertj.core.api.StringAssert
import org.junit.jupiter.api.Test

class AssertProviderTest {

    class StringAssertProvider(private val value: String) : AssertProvider<StringAssert> {
        override fun assertThat(): StringAssert {
            return value.assert()
        }
    }

    @Test
    fun `given StringAssertProvider when assert then StringAssert`() {
        val provider = StringAssertProvider("hello")
        provider.assert().isEqualTo("hello")
    }

    @Test
    fun `given AssertProvider when assert then isInstanceOf`() {
        val provider = StringAssertProvider("test")
        provider.assert().assert().isInstanceOf(StringAssert::class.java)
    }
}
