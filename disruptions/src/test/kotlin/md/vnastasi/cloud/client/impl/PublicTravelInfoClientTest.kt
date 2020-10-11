package md.vnastasi.cloud.client.impl

import assertk.assertThat
import assertk.assertions.*
import md.vnastasi.cloud.exception.ApiError
import md.vnastasi.cloud.exception.ApiErrorType
import md.vnastasi.cloud.exception.ApiException
import md.vnastasi.cloud.util.applicationProperties
import md.vnastasi.cloud.util.collecting
import md.vnastasi.cloud.util.enqueue
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient

class PublicTravelInfoClientTest {

    private val applicationProperties = applicationProperties()
    private val webServer = MockWebServer()
    private val webClient = WebClient.create(webServer.url("/").toString())

    private val client = PublicTravelInfoClientImpl(webClient, applicationProperties)

    @AfterEach
    fun tearDown() {
        webServer.shutdown()
    }

    @Test
    @DisplayName("When client returns HTTP 200 and valid body then expect flow of disturbance items")
    fun testSuccessfulResponse() {
        webServer.enqueue {
            body = "disturbances_and_notifications_wrapped.json"
        }

        val list = collecting { client.getAllDisruptions() }
        assertThat(list).hasSize(5)
    }

    @Test
    @DisplayName("When client returns HTTP 200 and empty payload then expect empty flow")
    fun testEmptyResponse() {
        webServer.enqueue {
            body = "empty_wrapped.json"
        }

        val list = collecting { client.getAllDisruptions() }
        assertThat(list).isEmpty()
    }

    @Test
    @DisplayName("When client returns HTTP 500 then expect exception")
    fun testErrorResponse() {
        webServer.enqueue {
            status = HttpStatus.INTERNAL_SERVER_ERROR
            body = "empty_wrapped.json"
        }

        assertThat { collecting { client.getAllDisruptions() } }
            .isFailure()
            .isInstanceOf(ApiException::class)
            .prop(ApiException::error).isDataClassEqualTo(ApiError(type = ApiErrorType.NS_SERVICE_FAILURE, message = "There was a problem contacting NS API portal"))
    }
}
