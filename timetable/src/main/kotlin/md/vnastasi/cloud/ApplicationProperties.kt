package md.vnastasi.cloud

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "application")
class ApplicationProperties {

    var nsApi: NsApiProperties = NsApiProperties()
}

class NsApiProperties {

    var publicTravelInfo: PublicTravelInfoProperties = PublicTravelInfoProperties()
    lateinit var baseUrl: String
}

class PublicTravelInfoProperties {

    var apiKey: ApiKeyProperties = ApiKeyProperties()
    lateinit var basePath: String
    lateinit var arrivalsPath: String
    lateinit var departuresPath: String
}

class ApiKeyProperties {

    lateinit var header: String
    lateinit var value: String
}
