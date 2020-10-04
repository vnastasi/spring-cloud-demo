package md.vnastasi.cloud.util

import assertk.Assert
import assertk.assertions.support.fail
import java.time.OffsetDateTime

fun Assert<OffsetDateTime>.isSameAs(expected: OffsetDateTime) = given { actual ->
    val expectedInstant = expected.toInstant()
    val actualInstant = actual.toInstant()
    if (actualInstant.compareTo(expectedInstant) == 0) return
    fail(expected, actual)
}
