package md.vnastasi.cloud.client

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.client.model.ArrivalWrapper
import md.vnastasi.cloud.client.model.DepartureWrapper

interface PublicTravelInfoClient {

    suspend fun getDepartures(uicCode: String): Flow<DepartureWrapper>

    suspend fun getArrivals(uicCode: String): Flow<ArrivalWrapper>
}
