package me.ahoo.test.asserts

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.BigDecimalAssert
import org.assertj.core.api.BooleanAssert
import org.assertj.core.api.ByteAssert
import org.assertj.core.api.CollectionAssert
import org.assertj.core.api.DoubleAssert
import org.assertj.core.api.FloatAssert
import org.assertj.core.api.IntegerAssert
import org.assertj.core.api.IteratorAssert
import org.assertj.core.api.ListAssert
import org.assertj.core.api.LongAssert
import org.assertj.core.api.MapAssert
import org.assertj.core.api.ObjectArrayAssert
import org.assertj.core.api.ObjectAssert
import org.assertj.core.api.OptionalAssert
import org.assertj.core.api.ShortAssert
import org.assertj.core.api.StringAssert
import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.Optional
import java.util.stream.Stream
import kotlin.jvm.java

class JdkTest {

    @Test
    fun `given Boolean when assert then BooleanAssert`() {
        val value = true
        assertThat(value.assert()).isInstanceOf(BooleanAssert::class.java)
    }

    @Test
    fun `given Byte when assert then ByteAssert`() {
        val value: Byte = 1
        assertThat(value.assert()).isInstanceOf(ByteAssert::class.java)
    }

    @Test
    fun `given Short when assert then ShortAssert`() {
        val value: Short = 1
        assertThat(value.assert()).isInstanceOf(ShortAssert::class.java)
    }

    @Test
    fun `given Int when assert then IntegerAssert`() {
        val value: Int = 1
        assertThat(value.assert()).isInstanceOf(IntegerAssert::class.java)
    }

    @Test
    fun `given Long when assert then LongAssert`() {
        val value: Long = 1
        assertThat(value.assert()).isInstanceOf(LongAssert::class.java)
    }

    @Test
    fun `given Float when assert then FloatAssert`() {
        val value: Float = 1.0F
        assertThat(value.assert()).isInstanceOf(FloatAssert::class.java)
    }

    @Test
    fun `given Double when assert then DoubleAssert`() {
        val value: Double = 1.0
        assertThat(value.assert()).isInstanceOf(DoubleAssert::class.java)
    }

    @Test
    fun `given BigDecimal when assert then BigDecimalAssert`() {
        val value: BigDecimal = BigDecimal.TEN
        assertThat(value.assert()).isInstanceOf(BigDecimalAssert::class.java)
    }

    @Test
    fun `given String when assert then StringAssert`() {
        val value: String = "1"
        assertThat(value.assert()).isInstanceOf(StringAssert::class.java)
    }

    @Test
    fun `given Object when assert then ObjectAssert`() {
        val value = Any()
        assertThat(value.assert()).isInstanceOf(ObjectAssert::class.java)
    }

    @Test
    fun `given Iterator when assert then IteratorAssert`() {
        val value = mockk<Iterator<String>>()
        assertThat(value.assert()).isInstanceOf(IteratorAssert::class.java)
    }

    @Test
    fun `given Collection when assert then CollectionAssert`() {
        val value = mockk<Collection<String>>()
        assertThat(value.assert()).isInstanceOf(CollectionAssert::class.java)
    }

    @Test
    fun `given Array when assert then ObjectArrayAssert`() {
        val value = arrayOf("1")
        assertThat(value.assert()).isInstanceOf(ObjectArrayAssert::class.java)
    }

    @Test
    fun `given List when assert then ListAssert`() {
        val value = listOf("1")
        assertThat(value.assert()).isInstanceOf(ListAssert::class.java)
    }

    @Test
    fun `given Throwable when assert then ThrowableAssert`() {
        val value = Throwable("1")
        assertThat(value.assert()).isInstanceOf(ThrowableAssert::class.java)
    }

    @Test
    fun `given Optional when assert then OptionalAssert`() {
        val value = Optional.of("1")
        assertThat(value.assert()).isInstanceOf(OptionalAssert::class.java)
    }

    @Test
    fun `given Map when assert then MapAssert`() {
        val value = mapOf("1" to "1")
        assertThat(value.assert()).isInstanceOf(MapAssert::class.java)
    }

    @Test
    fun `given Stream when assert then ListAssert`() {
        val value = mockk<Stream<String>>()
        assertThat(value.assert()).isInstanceOf(ListAssert::class.java)
    }
}
