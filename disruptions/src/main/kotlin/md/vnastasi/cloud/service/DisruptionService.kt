package md.vnastasi.cloud.service

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.endpoint.model.disturbance.Disturbance
import md.vnastasi.cloud.endpoint.model.notification.Notification

interface DisruptionService {

    suspend fun getNotifications(): Flow<Notification>

    suspend fun getDisturbances(): Flow<Disturbance>
}
