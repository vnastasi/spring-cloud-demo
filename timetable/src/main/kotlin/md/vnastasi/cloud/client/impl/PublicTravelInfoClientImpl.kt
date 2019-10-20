package md.vnastasi.cloud.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import md.vnastasi.cloud.ApplicationProperties
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.client.model.ArrivalWrapper
import md.vnastasi.cloud.client.model.ArrivalsResponseWrapper
import md.vnastasi.cloud.client.model.DepartureWrapper
import md.vnastasi.cloud.client.model.DeparturesResponseWrapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.client.bodyToFlow

private const val PARAM_UIC_CODE = "uicCode"

@Component
class PublicTravelInfoClientImpl(
    private val webClient: WebClient,
    private val applicationProperties: ApplicationProperties
) : PublicTravelInfoClient {

    override suspend fun getDepartures(uicCode: String): Flow<DepartureWrapper> {
        val url = applicationProperties.nsApi.publicTravelInfo.departuresPath
        return webClient.get()
            .uri { it.path(url).queryParam(PARAM_UIC_CODE, uicCode).build() }
            .awaitExchange()
            .bodyToFlow<DeparturesResponseWrapper>()
            .map { it.payload.departures }
            .flatMapMerge { it.asFlow() }
    }

    override suspend fun getArrivals(uicCode: String): Flow<ArrivalWrapper> {
        val url = applicationProperties.nsApi.publicTravelInfo.arrivalsPath
        return webClient.get()
            .uri { it.path(url).queryParam(PARAM_UIC_CODE, uicCode).build() }
            .awaitExchange()
            .bodyToFlow<ArrivalsResponseWrapper>()
            .map { it.payload.arrivals }
            .flatMapMerge { it.asFlow() }
    }
}
