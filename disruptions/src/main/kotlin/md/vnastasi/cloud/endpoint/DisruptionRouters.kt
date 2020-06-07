package md.vnastasi.cloud.endpoint

import md.vnastasi.cloud.service.DisruptionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class DisruptionRouters(
    private val disruptionService: DisruptionService
) {

    @Bean
    fun routes() = coRouter {
        GET("/notification", handleNotifications())
        GET("/disruption", handleDisruptions())
        GET("/disruption/{id}", handleDisruption())
    }

    private fun handleNotifications(): suspend (ServerRequest) -> ServerResponse = {
        val notifications = disruptionService.getNotifications()
        ServerResponse.ok().bodyAndAwait(notifications)
    }

    private fun handleDisruptions(): suspend (ServerRequest) -> ServerResponse = {
        val disruptions = disruptionService.getDisruptions()
        ServerResponse.ok().bodyAndAwait(disruptions)
    }

    private fun handleDisruption(): suspend (ServerRequest) -> ServerResponse = { request ->
        val id = request.pathVariable("id")
        val disruption = disruptionService.getDisruption(id)
        ServerResponse.ok().bodyValueAndAwait(disruption)
    }
}
