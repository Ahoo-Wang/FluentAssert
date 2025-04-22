package me.ahoo.test.asserts

import io.mockk.mockk
import org.assertj.core.api.DateAssert
import org.assertj.core.api.DurationAssert
import org.assertj.core.api.InstantAssert
import org.assertj.core.api.LocalDateAssert
import org.assertj.core.api.LocalDateTimeAssert
import org.assertj.core.api.LocalTimeAssert
import org.assertj.core.api.OffsetDateTimeAssert
import org.assertj.core.api.OffsetTimeAssert
import org.assertj.core.api.PeriodAssert
import org.assertj.core.api.TemporalAssert
import org.assertj.core.api.YearMonthAssert
import org.assertj.core.api.ZonedDateTimeAssert
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period
import java.time.YearMonth
import java.time.ZonedDateTime
import java.time.temporal.Temporal
import java.util.*

class JdkTimeTest {
    @Test
    fun `given Date when assert then DateAssert`() {
        val value = Date()
        value.assert().assert().isInstanceOf(DateAssert::class.java)
    }

    @Test
    fun `given ZonedDateTime when assert then ZonedDateTimeAssert`() {
        val value = ZonedDateTime.now()
        value.assert().assert().isInstanceOf(ZonedDateTimeAssert::class.java)
    }

    @Test
    fun `given Temporal when assert then TemporalAssert`() {
        val value = mockk<Temporal>()
        value.assert().assert().isInstanceOf(TemporalAssert::class.java)
    }

    @Test
    fun `given LocalDateTime when assert then LocalDateTimeAssert`() {
        val value = LocalDateTime.now()
        value.assert().assert().isInstanceOf(LocalDateTimeAssert::class.java)
    }

    @Test
    fun `given OffsetDateTime when assert then OffsetDateTimeAssert`() {
        val value = OffsetDateTime.now()
        value.assert().assert().isInstanceOf(OffsetDateTimeAssert::class.java)
    }

    @Test
    fun `given OffsetTime when assert then OffsetTimeAssert`() {
        val value = OffsetTime.now()
        value.assert().assert().isInstanceOf(OffsetTimeAssert::class.java)
    }

    @Test
    fun `given LocalTime when assert then LocalTimeAssert`() {
        val value = LocalTime.now()
        value.assert().assert().isInstanceOf(LocalTimeAssert::class.java)
    }

    @Test
    fun `given LocalDate when assert then LocalDateAssert`() {
        val value = LocalDate.now()
        value.assert().assert().isInstanceOf(LocalDateAssert::class.java)
    }

    @Test
    fun `given YearMonth when assert then YearMonthAssert`() {
        val value = YearMonth.now()
        value.assert().assert().isInstanceOf(YearMonthAssert::class.java)
    }

    @Test
    fun `given Instant when assert then InstantAssert`() {
        val value = Instant.now()
        value.assert().assert().isInstanceOf(InstantAssert::class.java)
    }

    @Test
    fun `given Duration when assert then DurationAssert`() {
        val value = Duration.ZERO
        value.assert().assert().isInstanceOf(DurationAssert::class.java)
    }

    @Test
    fun `given Period when assert then PeriodAssert`() {
        val value = Period.ZERO
        value.assert().assert().isInstanceOf(PeriodAssert::class.java)
    }
}
