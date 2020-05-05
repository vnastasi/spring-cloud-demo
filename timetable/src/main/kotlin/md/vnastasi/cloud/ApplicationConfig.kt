package md.vnastasi.cloud

import io.netty.bootstrap.Bootstrap
import md.vnastasi.cloud.log.HttpClientLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.channel.BootstrapHandlers
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient

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
            .clientConnector(ReactorClientHttpConnector(createHttpClient()))
            .baseUrl(url)
            .defaultHeader(headerName, headerValue)
            .build()
    }

    private fun createHttpClient(): HttpClient {
        val bootstrapFunction: (Bootstrap) -> Bootstrap = { BootstrapHandlers.updateLogSupport(it, HttpClientLogger(HttpClient::class)) }
        val tcpClientFunction: (TcpClient) -> TcpClient = { it.bootstrap(bootstrapFunction) }
        return HttpClient.create().tcpConfiguration(tcpClientFunction)
    }
}
