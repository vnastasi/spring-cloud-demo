package md.vnastasi.cloud.client.impl

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import md.vnastasi.cloud.client.model.*
import md.vnastasi.cloud.exception.ApiError
import md.vnastasi.cloud.exception.ApiErrorType
import md.vnastasi.cloud.exception.ApiException
import md.vnastasi.cloud.util.JsonUtils.readString
import md.vnastasi.cloud.util.applicationProperties
import md.vnastasi.cloud.util.isEqualTo
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

private const val UIC_CODE = "34567"

@FlowPreview
class PublicTravelInfoClientTest {

    private val applicationProperties = applicationProperties()
    private val webServer = MockWebServer()
    private val webClient = WebClient.create(webServer.url("/").toString())

    private val client = PublicTravelInfoClientImpl(webClient, applicationProperties)

    @AfterEach
    fun tearDown() {
        webServer.shutdown()
    }

    @Nested
    @DisplayName("Get Departures")
    inner class DeparturesTest {

        @Test
        @DisplayName("when client returns HTTP 200 then expect a flow of departures")
        fun testDepartures() = runTest {
            val mockResponse = MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(readString("departure_list.json"))
            webServer.enqueue(mockResponse)

            val list = client.getDepartures(UIC_CODE).toList(mutableListOf())

            assertThat(list).hasSize(2)

            assertThat(list[0]).all {
                prop(DepartureWrapper::direction).isEqualTo("Den Haag Centraal")
                prop(DepartureWrapper::plannedDateTime).isEqualTo("2019-10-25T19:02:00+0200")
                prop(DepartureWrapper::actualDateTime).isEqualTo("2019-10-25T19:02:00+0200")
                prop(DepartureWrapper::plannedTrack).isEqualTo("8")
                prop(DepartureWrapper::cancelled).isFalse()
                prop(DepartureWrapper::routeStations).isEmpty()
                prop(DepartureWrapper::departureStatus).isEqualTo(DepartureStatusWrapper.ON_STATION)
                prop(DepartureWrapper::messages).isNull()
                prop(DepartureWrapper::product).all {
                    prop(ProductWrapper::number).isEqualTo("2062")
                    prop(ProductWrapper::categoryCode).isEqualTo("IC")
                    prop(ProductWrapper::shortCategoryName).isEqualTo("NS Intercity")
                    prop(ProductWrapper::operatorName).isEqualTo("NS")
                    prop(ProductWrapper::operatorCode).isEqualTo("NS")
                    prop(ProductWrapper::type).isEqualTo(ProductTypeWrapper.TRAIN)
                }
            }

            assertThat(list[1]).all {
                prop(DepartureWrapper::direction).isEqualTo("'s-Hertogenbosch")
                prop(DepartureWrapper::plannedDateTime).isEqualTo("2019-10-25T19:15:00+0200")
                prop(DepartureWrapper::actualDateTime).isEqualTo("2019-10-25T19:15:00+0200")
                prop(DepartureWrapper::plannedTrack).isEqualTo("5")
                prop(DepartureWrapper::cancelled).isFalse()
                prop(DepartureWrapper::routeStations).containsExactly(
                    RouteStationWrapper(uicCode = "8400702", name = "Woerden"),
                    RouteStationWrapper(uicCode = "8400621", name = "Utrecht C."),
                    RouteStationWrapper(uicCode = "8400606", name = "Vaartsche Rijn"),
                    RouteStationWrapper(uicCode = "8400340", name = "Houten")
                )
                prop(DepartureWrapper::departureStatus).isEqualTo(DepartureStatusWrapper.INCOMING)
                prop(DepartureWrapper::messages).isNotNull().containsExactly(
                    MessageWrapper(
                        text = "De Intercity van 19:24 naar Groningen is eerder in Utrecht C. en vertrekt van spoor 3",
                        type = MessageTypeWrapper.INFO
                    )
                )
                prop(DepartureWrapper::product).all {
                    prop(ProductWrapper::number).isEqualTo("6973")
                    prop(ProductWrapper::categoryCode).isEqualTo("SPR")
                    prop(ProductWrapper::shortCategoryName).isEqualTo("NS Sprinter")
                    prop(ProductWrapper::operatorName).isEqualTo("NS")
                    prop(ProductWrapper::operatorCode).isEqualTo("NS")
                    prop(ProductWrapper::type).isEqualTo(ProductTypeWrapper.TRAIN)
                }
            }
        }

        @Test
        @DisplayName("when client returns an error then expect an exception")
        fun testError() = runTest {
            val mockResponse = MockResponse()
                .setResponseCode(HttpStatus.NOT_FOUND.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(readString("no_departures_for_station.json"))
            webServer.enqueue(mockResponse)

            assertThat { client.getDepartures(UIC_CODE).toList(mutableListOf()) }
                .isFailure()
                .isInstanceOf(ApiException::class)
                .prop(ApiException::error).isDataClassEqualTo(
                    ApiError(
                        type = ApiErrorType.NO_DEPARTURES_FOR_STATION,
                        message = "Er zijn geen vertrektijden voor station London St. Pancras Int."
                    )
                )
        }
    }

    @Nested
    @DisplayName("Get Arrivals")
    inner class ArrivalsTest {


        @Test
        @DisplayName("when client returns HTTP 200 then expect a flow of arrivals")
        fun testArrivals() = runTest {
            val mockResponse = MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(readString("arrival_list.json"))
            webServer.enqueue(mockResponse)

            val list = client.getArrivals(UIC_CODE).toList(mutableListOf())

            assertThat(list).hasSize(2)

            assertThat(list[0]).all {
                prop(ArrivalWrapper::origin).isEqualTo("Utrecht Centraal")
                prop(ArrivalWrapper::plannedDateTime).isEqualTo("2019-10-25T20:51:00+0200")
                prop(ArrivalWrapper::actualDateTime).isEqualTo("2019-10-25T20:51:00+0200")
                prop(ArrivalWrapper::plannedTrack).isEqualTo("8")
                prop(ArrivalWrapper::actualTrack).isEqualTo("8")
                prop(ArrivalWrapper::cancelled).isFalse()
                prop(ArrivalWrapper::messages).isNull()
                prop(ArrivalWrapper::product).all {
                    prop(ProductWrapper::number).isEqualTo("2870")
                    prop(ProductWrapper::categoryCode).isEqualTo("IC")
                    prop(ProductWrapper::shortCategoryName).isEqualTo("IC")
                    prop(ProductWrapper::operatorName).isEqualTo("NS")
                    prop(ProductWrapper::operatorCode).isEqualTo("NS")
                    prop(ProductWrapper::type).isEqualTo(ProductTypeWrapper.TRAIN)
                }
            }

            assertThat(list[1]).all {
                prop(ArrivalWrapper::origin).isEqualTo("Alphen a/d Rijn")
                prop(ArrivalWrapper::plannedDateTime).isEqualTo("2019-10-25T21:02:00+0200")
                prop(ArrivalWrapper::actualDateTime).isEqualTo("2019-10-25T21:02:00+0200")
                prop(ArrivalWrapper::plannedTrack).isEqualTo("11")
                prop(ArrivalWrapper::actualTrack).isEqualTo("11")
                prop(ArrivalWrapper::cancelled).isFalse()
                prop(ArrivalWrapper::messages).isNull()
                prop(ArrivalWrapper::product).all {
                    prop(ProductWrapper::number).isEqualTo("8667")
                    prop(ProductWrapper::categoryCode).isEqualTo("SPR")
                    prop(ProductWrapper::shortCategoryName).isEqualTo("SPR")
                    prop(ProductWrapper::operatorName).isEqualTo("R-net")
                    prop(ProductWrapper::operatorCode).isEqualTo("R-net")
                    prop(ProductWrapper::type).isEqualTo(ProductTypeWrapper.TRAIN)
                }
            }
        }

        @Test
        @DisplayName("when client returns an error then expect an exception")
        fun testError() = runTest {
            val mockResponse = MockResponse()
                .setResponseCode(HttpStatus.BAD_REQUEST.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(readString("station_not_found.json"))
            webServer.enqueue(mockResponse)

            assertThat { client.getArrivals(UIC_CODE).toList(mutableListOf()) }
                .isFailure()
                .isInstanceOf(ApiException::class)
                .prop(ApiException::error).isDataClassEqualTo(
                    ApiError(
                        type = ApiErrorType.UNKNOWN_STATION,
                        message = "Dit station is niet gevonden"
                    )
                )
        }
    }
}
