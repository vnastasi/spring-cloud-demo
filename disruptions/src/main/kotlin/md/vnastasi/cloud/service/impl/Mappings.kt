package md.vnastasi.cloud.service.impl

import md.vnastasi.cloud.client.model.DisruptionTypeWrapper
import md.vnastasi.cloud.client.model.disruption.DisruptionWrapper
import md.vnastasi.cloud.client.model.disruption.TravelAdviceListWrapper
import md.vnastasi.cloud.client.model.notification.NotificationWrapper
import md.vnastasi.cloud.endpoint.model.Disruption
import md.vnastasi.cloud.endpoint.model.DisruptionType
import md.vnastasi.cloud.endpoint.model.Notification
import md.vnastasi.cloud.endpoint.model.TravelAdvice

fun TravelAdviceListWrapper.asTravelAdvice(): TravelAdvice =
    TravelAdvice(summary = this.title, advice = this.travelAdvices.flatMap { it.advice })

fun DisruptionTypeWrapper.asDisruptionType(): DisruptionType =
    when (this) {
        DisruptionTypeWrapper.EVENT -> DisruptionType.EVENT
        DisruptionTypeWrapper.DISTURBANCE -> DisruptionType.DISTURBANCE
        DisruptionTypeWrapper.MAINTENANCE -> DisruptionType.MAINTENANCE
        else -> DisruptionType.PRIORITY_NOTIFICATION
    }

fun DisruptionWrapper.asDisruption(): Disruption =
    Disruption(
        id = this.id,
        summary = this.header,
        consequence = this.consequence,
        type = this.type.asDisruptionType(),
        travelAdvice = this.travelAdvices.asTravelAdvice(),
        start = this.validityList.map { it.startDate }.min() ?: throw IllegalStateException("Could not find earliest disruption start date"),
        end = this.validityList.map { it.endDate }.max() ?: throw IllegalStateException("Could not find latest disruption end date")
    )

fun NotificationWrapper.asNotification(): Notification =
    Notification(
        id = this.id,
        title = this.title,
        description = this.description,
        url = this.url,
        lastUpdate = this.lastUpdate,
        nextUpdate = if (this.nextUpdate.isAfter(this.lastUpdate)) this.nextUpdate else null
    )
