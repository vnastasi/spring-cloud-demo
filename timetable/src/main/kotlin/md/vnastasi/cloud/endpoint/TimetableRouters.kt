package md.vnastasi.cloud.endpoint

import md.vnastasi.cloud.service.TimetableService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class TimetableRouters(
    private val timetableService: TimetableService
) {

    @Bean
    fun routes() = coRouter {
        GET("/departures", handleDepartures())
        GET("/arrivals", handleArrivals())
    }

    private fun handleDepartures(): suspend (ServerRequest) -> ServerResponse = { request ->
        val code = request.queryParam("code").orElseThrow()
        val departures = timetableService.getDepartures(code)
        ServerResponse.ok().bodyAndAwait(departures)
    }

    private fun handleArrivals(): suspend (ServerRequest) -> ServerResponse = { request ->
        val code = request.queryParam("code").orElseThrow()
        val arrivals = timetableService.getArrivals(code)
        ServerResponse.ok().bodyAndAwait(arrivals)
    }
}