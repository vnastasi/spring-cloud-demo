package md.vnastasi.cloud.util

import md.vnastasi.cloud.ApiKeyProperties
import md.vnastasi.cloud.ApplicationProperties
import md.vnastasi.cloud.NsApiProperties
import md.vnastasi.cloud.PublicTravelInfoProperties

fun applicationProperties(
    block: ApplicationProperties.() -> Unit = {
        nsApi = nsApiProperties()
    }
) = ApplicationProperties().apply(block)

fun nsApiProperties(
    block: NsApiProperties.() -> Unit = {
        baseUrl = "https://stub"
        publicTravelInfo = publicTravelInfoProperties()
    }
) = NsApiProperties().apply(block)

fun publicTravelInfoProperties(
    block: PublicTravelInfoProperties.() -> Unit = {
        basePath = "/pti"
        departuresPath = "/departures"
        arrivalsPath = "/arrivals"
        apiKey = apiKeyProperties()
    }
) = PublicTravelInfoProperties().apply(block)

fun apiKeyProperties(
    block: ApiKeyProperties.() -> Unit = {
        header = "NS-API-KEY"
        value = "1234567890"
    }
) = ApiKeyProperties().apply(block)
