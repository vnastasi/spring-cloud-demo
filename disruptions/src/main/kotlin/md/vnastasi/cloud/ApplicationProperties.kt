package md.vnastasi.cloud

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
data class ApplicationProperties(
    val nsApi: NsApiProperties
)

data class NsApiProperties(
    val publicTravelInfo: PublicTravelInfoProperties,
    val baseUrl: String
)

data class PublicTravelInfoProperties(
    val apiKey: ApiKeyProperties,
    val basePath: String,
    val disruptionsPath: String
)

data class ApiKeyProperties(
    val header: String,
    val value: String
)
