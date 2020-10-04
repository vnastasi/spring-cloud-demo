package md.vnastasi.cloud.service.impl

import md.vnastasi.cloud.client.model.disruption.*
import md.vnastasi.cloud.endpoint.model.disturbance.Disturbance
import md.vnastasi.cloud.endpoint.model.disturbance.DisturbanceType
import md.vnastasi.cloud.endpoint.model.notification.Notification
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun NotificationTypeWrapper.asLevel(): Int = when (this) {
    NotificationTypeWrapper.PRIORITY_1 -> 1
    NotificationTypeWrapper.PRIORITY_2 -> 2
    NotificationTypeWrapper.PRIORITY_3 -> 3
}

fun NotificationWrapper.asNotification() = Notification(
    id = this.id,
    title = this.title,
    description = this.description,
    level = this.type.asLevel(),
    lastUpdate = this.lastUpdate,
    nextUpdate = this.nextUpdate,
    infoUrl = this.url
)

fun DisturbanceTypeWrapper.asDisruptionType(): DisturbanceType = when (this) {
    DisturbanceTypeWrapper.PRIORITY_1 -> DisturbanceType.HIGH_PRIORITY
    DisturbanceTypeWrapper.PRIORITY_2 -> DisturbanceType.MEDIUM_PRIORITY
    DisturbanceTypeWrapper.PRIORITY_3 -> DisturbanceType.LOW_PRIORITY
    DisturbanceTypeWrapper.DISRUPTION -> DisturbanceType.DISRUPTION
    DisturbanceTypeWrapper.MAINTENANCE -> DisturbanceType.MAINTENANCE
    DisturbanceTypeWrapper.EVENT -> DisturbanceType.EVENT
}

fun DisturbanceWrapper.asDisturbance() = Disturbance(
    id = this.id,
    type = this.type.asDisruptionType(),
    trajectory = this.header,
    start = this.findStartTime(),
    end = this.findEndTime(),
    cause = this.cause.orEmpty().capitalize()
)

private fun DisturbanceWrapper.findStartTime(): OffsetDateTime =
    when (this.type) {
        DisturbanceTypeWrapper.MAINTENANCE -> this.validityList?.minOf { it.start } ?: epochStart()
        else -> this.trajectories?.minOf { it.startTime } ?: epochStart()
    }

private fun DisturbanceWrapper.findEndTime(): OffsetDateTime =
    when (this.type) {
        DisturbanceTypeWrapper.MAINTENANCE -> this.validityList?.maxOf { it.end } ?: epochStart()
        else -> this.trajectories?.maxOf { it.endTime } ?: epochStart()
    }

private fun epochStart() = OffsetDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneOffset.UTC)