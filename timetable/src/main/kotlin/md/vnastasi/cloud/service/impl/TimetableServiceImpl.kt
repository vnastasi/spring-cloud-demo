package md.vnastasi.cloud.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure
import md.vnastasi.cloud.service.TimetableService
import org.springframework.stereotype.Service

@Service
class TimetableServiceImpl(
    private val client: PublicTravelInfoClient
) : TimetableService {

    override suspend fun getDepartures(code: String): Flow<Departure> =
        client.getDepartures(code).map { it.asDeparture() }

    override suspend fun getArrivals(code: String): Flow<Arrival> =
        client.getArrivals(code).map { it.asArrival() }
}
