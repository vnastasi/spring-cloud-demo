package md.vnastasi.cloud.util

import md.vnastasi.cloud.ApiKeyProperties
import md.vnastasi.cloud.ApplicationProperties
import md.vnastasi.cloud.NsApiProperties
import md.vnastasi.cloud.PublicTravelInfoProperties

fun applicationProperties(block: ApplicationPropertiesDsl.() -> Unit = {}): ApplicationProperties {
    val appDsl = ApplicationPropertiesDsl().apply(block)
    val nsApiDsl = appDsl.nsApi
    val ptiDsl = nsApiDsl.publicTravelInfo
    val apiKeyDsl = ptiDsl.apiKey
    return ApplicationProperties(
        nsApi = NsApiProperties(
            publicTravelInfo = PublicTravelInfoProperties(
                apiKey = ApiKeyProperties(header = apiKeyDsl.header, value = apiKeyDsl.value),
                basePath = ptiDsl.basePath,
                arrivalsPath = ptiDsl.arrivalsPath,
                departuresPath = ptiDsl.departuresPath
            ),
            baseUrl = nsApiDsl.baseUrl
        )
    )
}

fun nsApiProperties(block: NsApiPropertiesDsl.() -> Unit = {}) =
    NsApiPropertiesDsl().apply(block)

fun publicTravelInfoProperties(block: PublicTravelInfoPropertiesDsl.() -> Unit = {}) =
    PublicTravelInfoPropertiesDsl().apply(block)

fun apiKeyProperties(block: ApiKeyPropertiesDsl.() -> Unit = {}) =
    ApiKeyPropertiesDsl().apply(block)

data class ApplicationPropertiesDsl(
    var nsApi: NsApiPropertiesDsl = nsApiProperties()
)

data class NsApiPropertiesDsl(
    var baseUrl: String = "https://stub",
    var publicTravelInfo: PublicTravelInfoPropertiesDsl = publicTravelInfoProperties()
)

data class PublicTravelInfoPropertiesDsl(
    var basePath: String = "/pti",
    var departuresPath: String = "/departures",
    var arrivalsPath: String = "/arrivals",
    var apiKey: ApiKeyPropertiesDsl = apiKeyProperties()
)

data class ApiKeyPropertiesDsl(
    var header: String = "X-API-Key",
    var value: String = "1234567890"
)
