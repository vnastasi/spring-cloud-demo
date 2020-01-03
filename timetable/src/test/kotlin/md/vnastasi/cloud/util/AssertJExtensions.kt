package md.vnastasi.cloud.util

import org.assertj.core.api.AbstractOffsetDateTimeAssert
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun AbstractOffsetDateTimeAssert<*>.isEqualInUtc(
    expected: String,
    pattern: String = "yyyy-MM-dd'T'HH:mm:ssZ"
): AbstractOffsetDateTimeAssert<*> {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return isEqualTo(OffsetDateTime.parse(expected, formatter).withOffsetSameInstant(ZoneOffset.UTC))
}