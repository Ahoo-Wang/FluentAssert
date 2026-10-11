package me.ahoo.test.asserts

import org.assertj.core.api.AssertProvider
import org.assertj.core.api.BigDecimalAssert
import org.assertj.core.api.BooleanAssert
import org.assertj.core.api.ByteAssert
import org.assertj.core.api.CollectionAssert
import org.assertj.core.api.CompletableFutureAssert
import org.assertj.core.api.DateAssert
import org.assertj.core.api.DoubleAssert
import org.assertj.core.api.DurationAssert
import org.assertj.core.api.FileAssert
import org.assertj.core.api.FloatAssert
import org.assertj.core.api.FutureAssert
import org.assertj.core.api.GenericComparableAssert
import org.assertj.core.api.InstantAssert
import org.assertj.core.api.IntegerAssert
import org.assertj.core.api.IterableAssert
import org.assertj.core.api.IteratorAssert
import org.assertj.core.api.ListAssert
import org.assertj.core.api.LocalDateAssert
import org.assertj.core.api.LocalDateTimeAssert
import org.assertj.core.api.LocalTimeAssert
import org.assertj.core.api.LongAssert
import org.assertj.core.api.MapAssert
import org.assertj.core.api.ObjectArrayAssert
import org.assertj.core.api.ObjectAssert
import org.assertj.core.api.OffsetDateTimeAssert
import org.assertj.core.api.OffsetTimeAssert
import org.assertj.core.api.OptionalAssert
import org.assertj.core.api.PathAssert
import org.assertj.core.api.PeriodAssert
import org.assertj.core.api.PredicateAssert
import org.assertj.core.api.ShortAssert
import org.assertj.core.api.StringAssert
import org.assertj.core.api.TemporalAssert
import org.assertj.core.api.ThrowableAssert
import org.assertj.core.api.UriAssert
import org.assertj.core.api.UrlAssert
import org.assertj.core.api.YearMonthAssert
import org.assertj.core.api.ZonedDateTimeAssert
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import java.net.URI
import java.net.URL
import java.nio.file.Path
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
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Future
import java.util.concurrent.ScheduledFuture
import java.util.function.Predicate
import java.util.stream.Stream

/**
 * Locks the public contract of this library: which overload a static receiver type resolves to
 * and the exact static return type of that overload.
 *
 * Every check is a typed assignment, so a regression (a widened return type, a receiver that starts
 * resolving to another overload, or a new ambiguity) fails compilation instead of passing a runtime
 * `isInstanceOf` check. Receivers are `null` to also exercise nullable receiver support.
 *
 * The "documented fallbacks" section pins resolutions that are documented as limitations; changing
 * them is a deliberate, user-visible behavior change.
 */
class OverloadResolutionContractTest {

    @Test
    fun `primitives and text resolve to their dedicated assertions`() {
        val boolean: BooleanAssert = (null as Boolean?).assert()
        val byte: ByteAssert = (null as Byte?).assert()
        val short: ShortAssert = (null as Short?).assert()
        val int: IntegerAssert = (null as Int?).assert()
        val long: LongAssert = (null as Long?).assert()
        val float: FloatAssert = (null as Float?).assert()
        val double: DoubleAssert = (null as Double?).assert()
        val bigDecimal: BigDecimalAssert = (null as BigDecimal?).assert()
        val string: StringAssert = (null as String?).assert()
        listOf(boolean, byte, short, int, long, float, double, bigDecimal, string).forEach { it.isNull() }
    }

    @Test
    fun `generic receivers resolve to object and comparable assertions`() {
        val any: ObjectAssert<Any> = (null as Any?).assert()
        val comparable: GenericComparableAssert<JdkTest.ComparableObj> = (null as JdkTest.ComparableObj?).assert()
        any.isNull()
        comparable.isNull()
    }

    @Test
    fun `containers resolve to their most specific assertions`() {
        val iterable: IterableAssert<String> = (null as Iterable<String>?).assert()
        val iterator: IteratorAssert<String> = (null as Iterator<String>?).assert()
        val collection: CollectionAssert<String> = (null as Collection<String>?).assert()
        val set: CollectionAssert<String> = (null as Set<String>?).assert()
        val list: ListAssert<String> = (null as List<String>?).assert()
        val array: ObjectArrayAssert<String> = (null as Array<String>?).assert()
        val optional: OptionalAssert<String> = (null as Optional<String>?).assert()
        val map: MapAssert<String, Int> = (null as Map<String, Int>?).assert()
        val stream: ListAssert<String> = (null as Stream<String>?).assert()
        listOf(iterable, iterator, collection, set, list, array, optional, map, stream).forEach { it.isNull() }
    }

    @Test
    fun `time receivers resolve to their dedicated assertions`() {
        val date: DateAssert = (null as Date?).assert()
        val zonedDateTime: ZonedDateTimeAssert = (null as ZonedDateTime?).assert()
        val temporal: TemporalAssert = (null as Temporal?).assert()
        val localDateTime: LocalDateTimeAssert = (null as LocalDateTime?).assert()
        val offsetDateTime: OffsetDateTimeAssert = (null as OffsetDateTime?).assert()
        val offsetTime: OffsetTimeAssert = (null as OffsetTime?).assert()
        val localTime: LocalTimeAssert = (null as LocalTime?).assert()
        val localDate: LocalDateAssert = (null as LocalDate?).assert()
        val yearMonth: YearMonthAssert = (null as YearMonth?).assert()
        val instant: InstantAssert = (null as Instant?).assert()
        val duration: DurationAssert = (null as Duration?).assert()
        val period: PeriodAssert = (null as Period?).assert()
        listOf(
            date, zonedDateTime, temporal, localDateTime, offsetDateTime, offsetTime,
            localTime, localDate, yearMonth, instant, duration, period
        ).forEach { it.isNull() }
    }

    @Test
    fun `io receivers resolve to their dedicated assertions`() {
        val path: PathAssert = (null as Path?).assert()
        val file: FileAssert = (null as File?).assert()
        val url: UrlAssert = (null as URL?).assert()
        val uri: UriAssert = (null as URI?).assert()
        listOf(path, file, url, uri).forEach { it.isNull() }
    }

    @Test
    fun `concurrent receivers resolve to future assertions`() {
        val future: FutureAssert<String> = (null as Future<String>?).assert()
        val scheduledFuture: FutureAssert<String> = (null as ScheduledFuture<String>?).assert()
        val completableFuture: CompletableFutureAssert<String> = (null as CompletableFuture<String>?).assert()
        val completionStage: CompletableFutureAssert<String> = (null as CompletionStage<String>?).assert()
        listOf(future, scheduledFuture, completableFuture, completionStage).forEach { it.isNull() }
    }

    @Test
    fun `function throwable and provider receivers resolve to their assertions`() {
        val predicate: PredicateAssert<String> = (null as Predicate<String>?).assert()
        val throwable: ThrowableAssert<IllegalStateException> = (null as IllegalStateException?).assert()
        val provider: StringAssert = AssertProvider { "provided".assert() }.assert()
        predicate.isNull()
        throwable.isNull()
        provider.isEqualTo("provided")
    }

    @Test
    fun `documented fallbacks keep their resolution`() {
        val intArray: ObjectAssert<IntArray> = (null as IntArray?).assert()
        val charSequence: ObjectAssert<CharSequence> = (null as CharSequence?).assert()
        val stringBuilder: GenericComparableAssert<StringBuilder> = (null as StringBuilder?).assert()
        listOf(intArray, charSequence, stringBuilder).forEach { it.isNull() }
    }
}
