package md.vnastasi.cloud.service

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure
import md.vnastasi.cloud.exception.ApiException

interface TimetableService {

    @Throws(ApiException::class)
    suspend fun getDepartures(code: String): Flow<Departure>

    @Throws(ApiException::class)
    suspend fun getArrivals(code: String): Flow<Arrival>
}
