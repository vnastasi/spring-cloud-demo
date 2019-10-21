package md.vnastasi.cloud.service

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure

interface TimetableService {

    suspend fun getDepartures(code: String): Flow<Departure>

    suspend fun getArrivals(code: String): Flow<Arrival>
}
