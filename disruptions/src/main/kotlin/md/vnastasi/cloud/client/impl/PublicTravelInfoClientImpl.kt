package md.vnastasi.cloud.client.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import md.vnastasi.cloud.ApplicationProperties
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.client.model.BaseDisruptionWrapper
import md.vnastasi.cloud.client.model.disruption.DisruptionListResponseWrapper
import md.vnastasi.cloud.client.model.disruption.DisruptionResponseWrapper
import md.vnastasi.cloud.client.model.disruption.DisruptionWrapper
import md.vnastasi.cloud.client.model.error.ErrorResponseWrapper
import md.vnastasi.cloud.exception.ApiError
import md.vnastasi.cloud.exception.ApiErrorType
import md.vnastasi.cloud.exception.ApiException
import md.vnastasi.cloud.exception.asApiError
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

    override suspend fun getAllDisruptions(): Flow<BaseDisruptionWrapper> {
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
            .awaitBody<DisruptionListResponseWrapper>()
            .payload
            .asFlow()
    }

    override suspend fun getDisruption(id: String): DisruptionWrapper {
        val uriBuilder: (UriBuilder) -> URI = { builder ->
            builder.path(applicationProperties.nsApi.publicTravelInfo.disruptionsPath).pathSegment(id).build()
        }

        return webClient.get()
            .uri(uriBuilder)
            .retrieve()
            .onStatus(
                { status -> status != HttpStatus.OK },
                { response -> response.bodyToMono(ErrorResponseWrapper::class.java).map { ApiException(it.asApiError()) } }
            )
            .awaitBody<DisruptionResponseWrapper>()
            .payload
            .disruption
    }
}
