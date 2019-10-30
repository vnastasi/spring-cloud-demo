package md.vnastasi.cloud.endpoint

import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.flow.flowOf
import md.vnastasi.cloud.endpoint.model.Arrival
import md.vnastasi.cloud.endpoint.model.Departure
import md.vnastasi.cloud.exception.ApiError
import md.vnastasi.cloud.exception.ApiErrorType
import md.vnastasi.cloud.exception.ApiException
import md.vnastasi.cloud.service.TimetableService
import md.vnastasi.cloud.util.UIC_CODE
import md.vnastasi.cloud.util.createArrival
import md.vnastasi.cloud.util.createDeparture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.OffsetDateTime
import java.time.ZoneOffset

private val MAP_TYPE_REFERENCE = object : ParameterizedTypeReference<Map<String, String>>() {}

@WebFluxTest(controllers = [TimetableEndpoint::class, ApiErrorAttributes::class])
class TimetableEndpointTest {

    @MockBean
    private lateinit var mockService: TimetableService

    @Autowired
    private lateinit var client: WebTestClient

    @AfterEach
    internal fun tearDown() {
        reset(mockService)
    }

    @Nested
    @DisplayName("Get departures")
    inner class DeparturesTest {

        @Test
        @DisplayName("when non-empty list then expect HTTP 200 and list in response body")
        fun testDepartures() {
            val departure = createDeparture()
            mockService.stub {
                onBlocking { getDepartures(UIC_CODE) }.thenReturn(flowOf(departure))
            }

            val list = client.get()
                .uri { it.path("/departures").queryParam("code", UIC_CODE).build() }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Departure::class.java)
                .returnResult()
                .responseBody

            assertThat(list)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .usingComparatorForElementFieldsWithType(compareBy { it.withOffsetSameInstant(ZoneOffset.UTC) }, OffsetDateTime::class.java)
                .contains(departure)
        }

        @Test
        @DisplayName("when API error then expect HTTP 500 and error reason in response")
        fun testError() {
            val apiError = ApiError(type = ApiErrorType.NO_DEPARTURES_FOR_STATION, message = "Message")
            mockService.stub {
                onBlocking { getDepartures(UIC_CODE) }.thenAnswer { throw ApiException(apiError) }
            }

            val map = client.get()
                .uri { it.path("/departures").queryParam("code", UIC_CODE).build() }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError
                .expectBody(MAP_TYPE_REFERENCE)
                .returnResult()
                .responseBody

            assertThat(map)
                .isNotNull
                .isNotEmpty
                .containsEntry(KEY_REASON, apiError.type.name)
                .containsEntry(KEY_MESSAGE, apiError.message)
        }
    }

    @Nested
    @DisplayName("Get arrivals")
    inner class ArrivalsTest {

        @Test
        @DisplayName("when non-empty list then expect HTTP 200 and list in response body")
        internal fun testArrivals() {
            val arrival = createArrival()
            mockService.stub {
                onBlocking { getArrivals(UIC_CODE) }.thenReturn(flowOf(arrival))
            }

            val list = client.get()
                .uri { it.path("/arrivals").queryParam("code", UIC_CODE).build() }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Arrival::class.java)
                .returnResult()
                .responseBody

            assertThat(list)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .usingComparatorForElementFieldsWithType(compareBy { it.withOffsetSameInstant(ZoneOffset.UTC) }, OffsetDateTime::class.java)
                .contains(arrival)
        }

        @Test
        @DisplayName("when API error then expect HTTP 500 and error reason in response")
        fun testError() {
            val apiError = ApiError(type = ApiErrorType.UNKNOWN_STATION, message = "Message")
            mockService.stub {
                onBlocking { getArrivals(UIC_CODE) }.thenAnswer { throw ApiException(apiError) }
            }

            val map = client.get()
                .uri { it.path("/arrivals").queryParam("code", UIC_CODE).build() }
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError
                .expectBody(MAP_TYPE_REFERENCE)
                .returnResult()
                .responseBody

            assertThat(map)
                .isNotNull
                .isNotEmpty
                .containsEntry(KEY_REASON, apiError.type.name)
                .containsEntry(KEY_MESSAGE, apiError.message)
        }
    }
}
