package md.vnastasi.cloud.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.client.model.disruption.DisruptionPayloadWrapper
import md.vnastasi.cloud.client.model.notification.NotificationPayloadWrapper
import md.vnastasi.cloud.endpoint.model.Disruption
import md.vnastasi.cloud.endpoint.model.Notification
import md.vnastasi.cloud.service.DisruptionService
import org.springframework.stereotype.Service

@Service
class DisruptionServiceImpl(
    private val client: PublicTravelInfoClient
) : DisruptionService {

    override suspend fun getNotifications(): Flow<Notification> =
        client.getAllDisruptions()
            .mapNotNull { it as? NotificationPayloadWrapper }
            .map { it.notification }
            .distinctUntilChangedBy { it.id }
            .map { it.asNotification() }

    override suspend fun getDisruptions(): Flow<Disruption> =
        client.getAllDisruptions()
            .mapNotNull { it as? DisruptionPayloadWrapper }
            .map { it.disruption }
            .distinctUntilChangedBy { it.id }
            .map { it.asDisruption() }

    override suspend fun getDisruption(id: String): Disruption =
        client.getDisruption(id).asDisruption()
}
