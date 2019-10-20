package md.vnastasi.cloud.endpoint

import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure
import md.vnastasi.cloud.service.TimetableService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TimetableEndpoint(
    private val timetableService: TimetableService
) {

    @GetMapping("/departures")
    suspend fun getDepartures(@RequestParam code: String): ResponseEntity<List<Departure>> =
        timetableService.getDepartures(code).let { ResponseEntity.ok(it) }

    @GetMapping("/arrivals")
    suspend fun getArrivals(@RequestParam code: String): ResponseEntity<List<Arrival>> =
        timetableService.getArrivals(code).let { ResponseEntity.ok(it) }
}
