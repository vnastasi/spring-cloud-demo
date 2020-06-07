package md.vnastasi.cloud.client

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.client.model.BaseDisruptionWrapper
import md.vnastasi.cloud.client.model.disruption.DisruptionWrapper

interface PublicTravelInfoClient {

    suspend fun getAllDisruptions(): Flow<BaseDisruptionWrapper>

    suspend fun getDisruption(id: String): DisruptionWrapper
}
