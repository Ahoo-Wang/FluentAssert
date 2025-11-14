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

package me.ahoo.test.asserts

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatTemporal
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

/**
 * Creates a fluent assertion for Period objects.
 *
 * This extension function provides access to AssertJ's PeriodAssert methods for fluent testing
 * of Period objects, including null-safe operations and period property verification.
 *
 * Example:
 * ```kotlin
 * val period = Period.of(1, 2, 3)
 * period.assert().hasYears(1).hasMonths(2).hasDays(3)
 * ```
 *
 * @receiver Period? The period to assert on (nullable)
 * @return PeriodAssert A fluent assertion object for periods
 */
fun Date?.assert(): DateAssert = assertThat(this) as DateAssert

/**
 * Creates a fluent assertion for ZonedDateTime objects.
 *
 * This extension function provides access to AssertJ's ZonedDateTimeAssert methods for fluent testing
 * of ZonedDateTime objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val zonedDateTime = ZonedDateTime.now()
 * zonedDateTime.assert().isToday().hasZone(ZoneId.systemDefault())
 * ```
 *
 * @receiver ZonedDateTime? The zoned date-time to assert on (nullable)
 * @return ZonedDateTimeAssert A fluent assertion object for zoned date-times
 */
fun ZonedDateTime?.assert(): ZonedDateTimeAssert = assertThat(this) as ZonedDateTimeAssert

/**
 * Creates a fluent assertion for Temporal objects.
 *
 * This extension function provides access to AssertJ's TemporalAssert methods for fluent testing
 * of any Temporal objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val instant = Instant.now()
 * instant.assert().isBefore(Instant.now().plusSeconds(1))
 * ```
 *
 * @receiver Temporal? The temporal object to assert on (nullable)
 * @return TemporalAssert A fluent assertion object for temporal objects
 */
fun Temporal?.assert(): TemporalAssert = assertThatTemporal(this)

/**
 * Creates a fluent assertion for LocalDateTime objects.
 *
 * This extension function provides access to AssertJ's LocalDateTimeAssert methods for fluent testing
 * of LocalDateTime objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val dateTime = LocalDateTime.now()
 * dateTime.assert().isToday().isBefore(LocalDateTime.now().plusHours(1))
 * ```
 *
 * @receiver LocalDateTime? The local date-time to assert on (nullable)
 * @return LocalDateTimeAssert A fluent assertion object for local date-times
 */
fun LocalDateTime?.assert(): LocalDateTimeAssert = assertThat(this) as LocalDateTimeAssert

/**
 * Creates a fluent assertion for OffsetDateTime objects.
 *
 * This extension function provides access to AssertJ's OffsetDateTimeAssert methods for fluent testing
 * of OffsetDateTime objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val offsetDateTime = OffsetDateTime.now()
 * offsetDateTime.assert().isToday().hasOffset(ZoneOffset.UTC)
 * ```
 *
 * @receiver OffsetDateTime? The offset date-time to assert on (nullable)
 * @return OffsetDateTimeAssert A fluent assertion object for offset date-times
 */
fun OffsetDateTime?.assert(): OffsetDateTimeAssert = assertThat(this) as OffsetDateTimeAssert

/**
 * Creates a fluent assertion for OffsetTime objects.
 *
 * This extension function provides access to AssertJ's OffsetTimeAssert methods for fluent testing
 * of OffsetTime objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val offsetTime = OffsetTime.now()
 * offsetTime.assert().isBefore(OffsetTime.now().plusHours(1))
 * ```
 *
 * @receiver OffsetTime? The offset time to assert on (nullable)
 * @return OffsetTimeAssert A fluent assertion object for offset times
 */
fun OffsetTime?.assert(): OffsetTimeAssert = assertThat(this) as OffsetTimeAssert

/**
 * Creates a fluent assertion for LocalTime objects.
 *
 * This extension function provides access to AssertJ's LocalTimeAssert methods for fluent testing
 * of LocalTime objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val time = LocalTime.of(10, 30)
 * time.assert().isBefore(LocalTime.of(12, 0)).hasHour(10)
 * ```
 *
 * @receiver LocalTime? The local time to assert on (nullable)
 * @return LocalTimeAssert A fluent assertion object for local times
 */
fun LocalTime?.assert(): LocalTimeAssert = assertThat(this) as LocalTimeAssert

/**
 * Creates a fluent assertion for LocalDate objects.
 *
 * This extension function provides access to AssertJ's LocalDateAssert methods for fluent testing
 * of LocalDate objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val date = LocalDate.of(2023, 12, 25)
 * date.assert().isBefore(LocalDate.of(2024, 1, 1)).hasYear(2023)
 * ```
 *
 * @receiver LocalDate? The local date to assert on (nullable)
 * @return LocalDateAssert A fluent assertion object for local dates
 */
fun LocalDate?.assert(): LocalDateAssert = assertThat(this) as LocalDateAssert

/**
 * Creates a fluent assertion for YearMonth objects.
 *
 * This extension function provides access to AssertJ's YearMonthAssert methods for fluent testing
 * of YearMonth objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val yearMonth = YearMonth.of(2023, 12)
 * yearMonth.assert().hasYear(2023).hasMonth(12)
 * ```
 *
 * @receiver YearMonth? The year-month to assert on (nullable)
 * @return YearMonthAssert A fluent assertion object for year-months
 */
fun YearMonth?.assert(): YearMonthAssert = assertThat(this) as YearMonthAssert

/**
 * Creates a fluent assertion for Instant objects.
 *
 * This extension function provides access to AssertJ's InstantAssert methods for fluent testing
 * of Instant objects, including null-safe operations and temporal property verification.
 *
 * Example:
 * ```kotlin
 * val instant = Instant.now()
 * instant.assert().isBefore(Instant.now().plusSeconds(1))
 * ```
 *
 * @receiver Instant? The instant to assert on (nullable)
 * @return InstantAssert A fluent assertion object for instants
 */
fun Instant?.assert(): InstantAssert = assertThat(this) as InstantAssert

/**
 * Creates a fluent assertion for Duration objects.
 *
 * This extension function provides access to AssertJ's DurationAssert methods for fluent testing
 * of Duration objects, including null-safe operations and duration property verification.
 *
 * Example:
 * ```kotlin
 * val duration = Duration.ofHours(2)
 * duration.assert().hasHours(2).isGreaterThan(Duration.ofHours(1))
 * ```
 *
 * @receiver Duration? The duration to assert on (nullable)
 * @return DurationAssert A fluent assertion object for durations
 */
fun Duration?.assert(): DurationAssert = assertThat(this) as DurationAssert

/**
 * Creates a fluent assertion for Period objects.
 *
 * This extension function provides access to AssertJ's PeriodAssert methods for fluent testing
 * of Period objects, including null-safe operations and period property verification.
 *
 * Example:
 * ```kotlin
 * val period = Period.of(1, 2, 3)
 * period.assert().hasYears(1).hasMonths(2).hasDays(3)
 * ```
 *
 * @receiver Period? The period to assert on (nullable)
 * @return PeriodAssert A fluent assertion object for periods
 */
fun Period?.assert(): PeriodAssert = assertThat(this) as PeriodAssert
