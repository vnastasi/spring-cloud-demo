package md.vnastasi.cloud.service.impl

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure
import md.vnastasi.cloud.service.TimetableService
import org.springframework.stereotype.Service

@Service
class TimetableServiceImpl(
    private val client: PublicTravelInfoClient
) : TimetableService {

    override suspend fun getDepartures(code: String): List<Departure> =
        client.getDepartures(code).map { it.asDeparture() }.toCollection(mutableListOf())

    override suspend fun getArrivals(code: String): List<Arrival> =
        client.getArrivals(code).map { it.asArrival() }.toCollection(mutableListOf())
}
