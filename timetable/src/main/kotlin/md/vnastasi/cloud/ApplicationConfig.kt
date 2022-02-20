package md.vnastasi.cloud

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ApplicationConfig {

    @Bean
    fun provideWebClient(
        webClientBuilder: WebClient.Builder,
        applicationProperties: ApplicationProperties
    ): WebClient {
        val url = applicationProperties.nsApi.baseUrl + applicationProperties.nsApi.publicTravelInfo.basePath
        val headerName = applicationProperties.nsApi.publicTravelInfo.apiKey.header
        val headerValue = applicationProperties.nsApi.publicTravelInfo.apiKey.value

        return webClientBuilder
            .baseUrl(url)
            .defaultHeader(headerName, headerValue)
            .build()
    }
}
