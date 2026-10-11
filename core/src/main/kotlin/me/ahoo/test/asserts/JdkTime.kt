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

// Date, Instant, Duration and Period use their public AssertJ constructors. The other
// assertion classes have protected constructors and AssertJ's assertThat factories return
// wildcard-typed self-references (e.g. AbstractLocalDateAssert<?>), so those overloads cast
// the factory result to the concrete assertion class.

/**
 * Returns a [DateAssert] for this date.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.date
 */
fun Date?.assert(): DateAssert = DateAssert(this)

/**
 * Returns a [ZonedDateTimeAssert] for this date-time.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.zonedDateTime
 */
fun ZonedDateTime?.assert(): ZonedDateTimeAssert = assertThat(this) as ZonedDateTimeAssert

/**
 * Returns a [TemporalAssert] for this temporal.
 *
 * Only used when the static type is [Temporal]; concrete `java.time` types resolve to their dedicated assertions.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.temporal
 */
fun Temporal?.assert(): TemporalAssert = assertThatTemporal(this)

/**
 * Returns a [LocalDateTimeAssert] for this date-time.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.localDateTime
 */
fun LocalDateTime?.assert(): LocalDateTimeAssert = assertThat(this) as LocalDateTimeAssert

/**
 * Returns an [OffsetDateTimeAssert] for this date-time.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.offsetDateTime
 */
fun OffsetDateTime?.assert(): OffsetDateTimeAssert = assertThat(this) as OffsetDateTimeAssert

/**
 * Returns an [OffsetTimeAssert] for this time.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.offsetTime
 */
fun OffsetTime?.assert(): OffsetTimeAssert = assertThat(this) as OffsetTimeAssert

/**
 * Returns a [LocalTimeAssert] for this time.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.localTime
 */
fun LocalTime?.assert(): LocalTimeAssert = assertThat(this) as LocalTimeAssert

/**
 * Returns a [LocalDateAssert] for this date.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.localDate
 */
fun LocalDate?.assert(): LocalDateAssert = assertThat(this) as LocalDateAssert

/**
 * Returns a [YearMonthAssert] for this year-month.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.yearMonth
 */
fun YearMonth?.assert(): YearMonthAssert = assertThat(this) as YearMonthAssert

/**
 * Returns an [InstantAssert] for this instant.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.instant
 */
fun Instant?.assert(): InstantAssert = InstantAssert(this)

/**
 * Returns a [DurationAssert] for this duration.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.duration
 */
fun Duration?.assert(): DurationAssert = DurationAssert(this)

/**
 * Returns a [PeriodAssert] for this period.
 *
 * @sample me.ahoo.test.asserts.samples.JdkTimeSamples.period
 */
fun Period?.assert(): PeriodAssert = PeriodAssert(this)
