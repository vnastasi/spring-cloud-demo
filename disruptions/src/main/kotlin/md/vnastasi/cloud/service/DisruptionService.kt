package md.vnastasi.cloud.service

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.endpoint.model.Disruption
import md.vnastasi.cloud.endpoint.model.Notification

interface DisruptionService {

    suspend fun getNotifications(): Flow<Notification>

    suspend fun getDisruptions(): Flow<Disruption>

    suspend fun getDisruption(id: String): Disruption
}
