package md.vnastasi.cloud.client

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.client.model.disruption.DisruptionItemWrapper


interface PublicTravelInfoClient {

    suspend fun getAllDisruptions(): Flow<DisruptionItemWrapper>
}
