package md.vnastasi.cloud.client.impl

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import md.vnastasi.cloud.client.model.DepartureStatusWrapper
import md.vnastasi.cloud.client.model.MessageTypeWrapper
import md.vnastasi.cloud.client.model.MessageWrapper
import md.vnastasi.cloud.client.model.ProductTypeWrapper
import md.vnastasi.cloud.util.JsonUtils.readString
import md.vnastasi.cloud.util.applicationProperties
import md.vnastasi.cloud.util.isEqualInUtc
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

private const val UIC_CODE = "34567"

internal class PublicTravelInfoClientTest {

    private val applicationProperties = applicationProperties()
    private val webServer = MockWebServer()
    private val webClient = WebClient.create(webServer.url("/").toString())

    private val client = PublicTravelInfoClientImpl(webClient, applicationProperties)

    @AfterEach
    internal fun tearDown() {
        webServer.shutdown()
    }

    @Test
    @DisplayName("when client returns HTTP 200 then expect a flow of departures")
    internal fun testDepartures() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpStatus.OK.value())
            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody(readString("departure_list.json"))
        webServer.enqueue(mockResponse)

        val list = runBlocking { client.getDepartures(UIC_CODE).toList(mutableListOf()) }

        assertThat(list).hasSize(2)

        with(list[0]) {
            assertThat(direction).isEqualTo("Den Haag Centraal")
            assertThat(plannedDateTime).isEqualInUtc("2019-10-25T19:02:00+0200")
            assertThat(actualDateTime).isEqualInUtc("2019-10-25T19:02:00+0200")
            assertThat(plannedTrack).isEqualTo("8")
            assertThat(cancelled).isFalse()
            assertThat(routeStations).isEmpty()
            assertThat(departureStatus).isEqualTo(DepartureStatusWrapper.ON_STATION)
            assertThat(messages).isNull()
            with(product) {
                assertThat(number).isEqualTo("2062")
                assertThat(categoryCode).isEqualTo("IC")
                assertThat(shortCategoryName).isEqualTo("NS Intercity")
                assertThat(operatorName).isEqualTo("NS")
                assertThat(operatorCode).isEqualTo("NS")
                assertThat(type).isEqualTo(ProductTypeWrapper.TRAIN)
            }
        }

        with(list[1]) {
            assertThat(direction).isEqualTo("'s-Hertogenbosch")
            assertThat(plannedDateTime).isEqualInUtc("2019-10-25T19:15:00+0200")
            assertThat(actualDateTime).isEqualInUtc("2019-10-25T19:15:00+0200")
            assertThat(plannedTrack).isEqualTo("5")
            assertThat(cancelled).isFalse()
            assertThat(routeStations).extracting("name").contains("Woerden", "Utrecht C.", "Vaartsche Rijn", "Houten")
            assertThat(departureStatus).isEqualTo(DepartureStatusWrapper.INCOMING)
            assertThat(messages).hasSize(1).contains(MessageWrapper("De Intercity van 19:24 naar Groningen is eerder in Utrecht C. en vertrekt van spoor 3", MessageTypeWrapper.INFO))
            with(product) {
                assertThat(number).isEqualTo("6973")
                assertThat(categoryCode).isEqualTo("SPR")
                assertThat(shortCategoryName).isEqualTo("NS Sprinter")
                assertThat(operatorName).isEqualTo("NS")
                assertThat(operatorCode).isEqualTo("NS")
                assertThat(type).isEqualTo(ProductTypeWrapper.TRAIN)
            }
        }
    }
}
