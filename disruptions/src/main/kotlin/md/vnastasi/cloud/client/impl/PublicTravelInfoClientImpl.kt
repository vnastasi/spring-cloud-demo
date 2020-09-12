package md.vnastasi.cloud.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import md.vnastasi.cloud.ApplicationProperties
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.client.model.disruption.DisruptionItemWrapper
import md.vnastasi.cloud.client.model.disruption.DisruptionResponseWrapper
import md.vnastasi.cloud.exception.ApiError
import md.vnastasi.cloud.exception.ApiErrorType
import md.vnastasi.cloud.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.net.URI

private const val PARAM_LANGUAGE = "lang"
private const val DEFAULT_LANGUAGE = "en"

@Component
class PublicTravelInfoClientImpl(
    private val webClient: WebClient,
    private val applicationProperties: ApplicationProperties
) : PublicTravelInfoClient {

    override suspend fun getAllDisruptions(): Flow<DisruptionItemWrapper> {
        val uriBuilder: (UriBuilder) -> URI = { builder ->
            builder
                .path(applicationProperties.nsApi.publicTravelInfo.disruptionsPath)
                .queryParam(PARAM_LANGUAGE, DEFAULT_LANGUAGE)
                .build()
        }

        return webClient.get()
            .uri(uriBuilder)
            .retrieve()
            .onStatus(
                { status -> status != HttpStatus.OK },
                { Mono.error(ApiException(ApiError(type = ApiErrorType.NS_SERVICE_FAILURE, message = "There was a problem contacting NS API portal"))) }
            )
            .awaitBody<DisruptionResponseWrapper>()
            .payload
            .asFlow()
    }
}
