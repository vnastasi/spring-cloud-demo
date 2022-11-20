package md.vnastasi.cloud.util

import assertk.Assert
import assertk.assertions.support.fail
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun Assert<OffsetDateTime?>.isEqualTo(
    expected: String,
    pattern: String = "yyyy-MM-dd'T'HH:mm:ssZ"
) = given { actual ->
    val formatter = DateTimeFormatter.ofPattern(pattern)
    if (actual?.isEqual(OffsetDateTime.parse(expected, formatter).withOffsetSameInstant(java.time.ZoneOffset.UTC)) == true) {
        return
    }
    fail(expected, actual)
}
