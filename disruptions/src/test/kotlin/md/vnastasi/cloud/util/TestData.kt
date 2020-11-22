package md.vnastasi.cloud.util

import md.vnastasi.cloud.endpoint.model.disturbance.Disturbance
import md.vnastasi.cloud.endpoint.model.disturbance.DisturbanceType
import md.vnastasi.cloud.endpoint.model.notification.Notification
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun createNotification() = Notification(
    id = "1",
    infoUrl = "url",
    level = 1,
    title = "Notification",
    description = "Description",
    lastUpdate = "2020-12-01T10:05:00Z".asOffsetDateTime(),
    nextUpdate = "2020-12-31T23:00:00Z".asOffsetDateTime()
)

fun createDisturbance() = Disturbance(
    id = "1",
    type = DisturbanceType.MAINTENANCE,
    cause = "A cause",
    start = "2020-12-09T10:00:00Z".asOffsetDateTime(),
    end = "2020-12-25T23:59:59Z".asOffsetDateTime(),
    trajectory = "Amsterdam - Utrecht"
)

fun String.asOffsetDateTime(): OffsetDateTime =
    OffsetDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"))
