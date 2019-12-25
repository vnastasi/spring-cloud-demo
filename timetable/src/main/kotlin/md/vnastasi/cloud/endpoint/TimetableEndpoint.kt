package md.vnastasi.cloud.endpoint

import kotlinx.coroutines.flow.Flow
import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure
import md.vnastasi.cloud.service.TimetableService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TimetableEndpoint(
    private val timetableService: TimetableService
) {

    @GetMapping("/departures")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getDepartures(@RequestParam code: String): Flow<Departure> =
        timetableService.getDepartures(code)

    @GetMapping("/arrivals")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getArrivals(@RequestParam code: String): Flow<Arrival> =
        timetableService.getArrivals(code)
}
