package md.vnastasi.cloud.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.endpoint.model.disturbance.Disturbance
import md.vnastasi.cloud.endpoint.model.notification.Notification
import md.vnastasi.cloud.service.DisruptionService
import org.springframework.stereotype.Service

@Service
class DisruptionServiceImpl(
    private val client: PublicTravelInfoClient
) : DisruptionService {

    override suspend fun getNotifications(): Flow<Notification> =
        client.getAllDisruptions()
            .mapNotNull { it.notification }
            .map { it.asNotification() }

    override suspend fun getDisturbances(): Flow<Disturbance> =
        client.getAllDisruptions()
            .mapNotNull { it.disturbance }
            .map { it.asDisturbance() }
}
