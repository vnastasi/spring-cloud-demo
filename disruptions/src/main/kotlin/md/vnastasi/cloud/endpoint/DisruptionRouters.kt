package md.vnastasi.cloud.endpoint

import md.vnastasi.cloud.service.DisruptionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class DisruptionRouters(
    private val disruptionService: DisruptionService
) {

    @Bean
    fun routes() = coRouter {
        GET("/notification", handleNotifications())
        GET("/disturbance", handleDisruptions())
    }

    private fun handleNotifications(): suspend (ServerRequest) -> ServerResponse = {
        ServerResponse.ok().bodyAndAwait(disruptionService.getNotifications())
    }

    private fun handleDisruptions(): suspend (ServerRequest) -> ServerResponse = {
        ServerResponse.ok().bodyAndAwait(disruptionService.getDisturbances())
    }
}
