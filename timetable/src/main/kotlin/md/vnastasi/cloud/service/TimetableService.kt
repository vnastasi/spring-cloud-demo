package md.vnastasi.cloud.service

import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure

interface TimetableService {

    suspend fun getDepartures(code: String): List<Departure>

    suspend fun getArrivals(code: String): List<Arrival>
}
