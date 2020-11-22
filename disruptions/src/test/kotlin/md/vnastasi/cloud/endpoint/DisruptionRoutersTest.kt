package md.vnastasi.cloud.endpoint

import assertk.assertThat
import assertk.assertions.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.flow.flowOf
import md.vnastasi.cloud.endpoint.model.disturbance.Disturbance
import md.vnastasi.cloud.endpoint.model.notification.Notification
import md.vnastasi.cloud.service.DisruptionService
import md.vnastasi.cloud.util.createDisturbance
import md.vnastasi.cloud.util.createNotification
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
@Import(value = [DisruptionRouters::class, ApiErrorAttributes::class])
class DisruptionRoutersTest {

    @MockBean
    private lateinit var mockService: DisruptionService

    @Autowired
    private lateinit var client: WebTestClient

    @AfterEach
    fun tearDown() {
        reset(mockService)
    }

    @Nested
    @DisplayName("Notifications")
    inner class NotificationsTest {

        @Test
        @DisplayName("when non-empty list then expect HTTP 200 and list in response body")
        fun testNotifications() {
            val notification = createNotification()
            mockService.stub {
                onBlocking { getNotifications() } doReturn flowOf(notification)
            }

            val list = client.get()
                .uri("/notification")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Notification::class.java)
                .returnResult()
                .responseBody

            assertThat(list).isNotNull().hasSize(1)
            assertThat(list?.get(0)).isEqualTo(notification)
        }
    }

    @Nested
    @DisplayName("Disturbances")
    inner class DisturbancesTest {

        @Test
        @DisplayName("when non-empty list then expect HTTP 200 and list in response body")
        fun testDisturbances() {
            val disturbance = createDisturbance()
            mockService.stub {
                onBlocking { getDisturbances(any()) } doReturn flowOf(disturbance)
            }

            val list = client.get()
                .uri("/disturbance")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Disturbance::class.java)
                .returnResult()
                .responseBody

            assertThat(list).isNotNull().hasSize(1)
            assertThat(list?.get(0)).isEqualTo(disturbance)
        }

        @Test
        @DisplayName("when non-empty list then expect HTTP 200 and list in response body")
        fun testDisturbancesWithPeriod() {
            val disturbance = createDisturbance()
            mockService.stub {
                onBlocking { getDisturbances(any()) } doReturn flowOf(disturbance)
            }

            val list = client.get()
                .uri { it.path("/disturbance").queryParam("start", "2020-12-01T12:00:00Z").queryParam("end", "2020-12-05T23:00:00Z").build() }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Disturbance::class.java)
                .returnResult()
                .responseBody

            assertThat(list).isNotNull().hasSize(1)
            assertThat(list?.get(0)).isEqualTo(disturbance)
        }
    }
}