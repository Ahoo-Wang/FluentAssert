/*
 * Copyright [2021-present] [ahoo wang <ahoowang@qq.com> (https://github.com/Ahoo-Wang)].
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ahoo.test.asserts.samples

import me.ahoo.test.asserts.assert
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.Temporal
import java.util.*

/**
 * Executable samples rendered into the KDoc of `JdkTime.kt` via `@sample`.
 */
class JdkTimeSamples {

    @Test
    fun date() {
        val date = Date()
        date.assert().isToday()
    }

    @Test
    fun zonedDateTime() {
        val dateTime = ZonedDateTime.of(2026, 10, 1, 9, 0, 0, 0, ZoneId.of("Asia/Shanghai"))
        dateTime.assert().isBefore(dateTime.plusHours(1)).isAfter(dateTime.minusHours(1))
    }

    @Test
    fun temporal() {
        // Only a receiver statically typed as Temporal uses this overload.
        val temporal: Temporal = Instant.parse("2026-01-01T00:00:00Z")
        temporal.assert().isEqualTo(Instant.parse("2026-01-01T00:00:00Z"))
    }

    @Test
    fun localDateTime() {
        val dateTime = LocalDateTime.of(2026, Month.OCTOBER, 1, 9, 30)
        dateTime.assert().isBefore(dateTime.plusHours(1)).hasHour(9)
    }

    @Test
    fun offsetDateTime() {
        val dateTime = OffsetDateTime.of(2026, 10, 1, 9, 30, 0, 0, ZoneOffset.UTC)
        dateTime.assert().isBefore(dateTime.plusDays(1)).isAfter(dateTime.minusDays(1))
    }

    @Test
    fun offsetTime() {
        val time = OffsetTime.of(9, 30, 0, 0, ZoneOffset.UTC)
        time.assert().isBefore(time.plusHours(1)).isAfter(time.minusHours(1))
    }

    @Test
    fun localTime() {
        val time = LocalTime.of(10, 30)
        time.assert().isBefore(LocalTime.NOON).hasHour(10).hasMinute(30)
    }

    @Test
    fun localDate() {
        val date = LocalDate.of(2026, Month.OCTOBER, 1)
        date.assert().hasYear(2026).hasMonth(Month.OCTOBER).hasDayOfMonth(1)
    }

    @Test
    fun yearMonth() {
        val yearMonth = YearMonth.of(2026, Month.OCTOBER)
        yearMonth.assert().hasYear(2026).hasMonth(Month.OCTOBER)
    }

    @Test
    fun instant() {
        val instant = Instant.parse("2026-10-01T00:00:00Z")
        instant.assert().isBefore(instant.plusSeconds(1))
    }

    @Test
    fun duration() {
        val duration = Duration.ofHours(2)
        duration.assert().hasHours(2).isGreaterThan(Duration.ofHours(1))
    }

    @Test
    fun period() {
        val period = Period.of(1, 2, 3)
        period.assert().hasYears(1).hasMonths(2).hasDays(3)
    }
}
