package md.vnastasi.cloud.service.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.endpoint.model.DepartureStatus
import md.vnastasi.cloud.endpoint.model.TransportationType
import md.vnastasi.cloud.util.JsonUtils.readObject
import md.vnastasi.cloud.util.isEqualInUtc
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private const val UIC_CODE = "34567"

class TimetableServiceTest {

    private val mockClient = mock<PublicTravelInfoClient>()

    private val service = TimetableServiceImpl(mockClient)

    @Nested
    inner class DeparturesTest {

        @Test
        internal fun testDepartures() {
            mockClient.stub {
                onBlocking { getDepartures(UIC_CODE) }.thenReturn(flowOf(readObject("departure_item.json")))
            }

            val list = runBlocking { service.getDepartures(UIC_CODE).toList(mutableListOf()) }

            assertThat(list).hasSize(1)

            with(list[0]) {
                assertThat(destination).isEqualTo("Den Haag Centraal")
                assertThat(plannedDeparture).isEqualInUtc("2019-10-25T19:02:00+0200")
                assertThat(actualDeparture).isEqualInUtc("2019-10-25T19:02:00+0200")
                assertThat(plannedTrack).isEqualTo("8")
                assertThat(cancelled).isFalse()
                assertThat(intermediateStations).isEmpty()
                assertThat(messages).isEmpty()
                assertThat(status).isEqualTo(DepartureStatus.BOARDING)
                with(unit) {
                    assertThat(number).isEqualTo("NS 2062")
                    assertThat(type).isEqualTo(TransportationType.TRAIN)
                    assertThat(category.code).isEqualTo("IC")
                    assertThat(category.name).isEqualTo("Intercity")
                    assertThat(operator).isEqualTo("NS")
                }
            }
        }

        @Test
        internal fun testError() {
            mockClient.stub {
                onBlocking { getDepartures(UIC_CODE) }.thenThrow(IllegalStateException::class.java)
            }

            assertThatThrownBy { runBlocking { service.getDepartures(UIC_CODE) } }
                .isInstanceOf(IllegalStateException::class.java)
        }
    }

    @Nested
    inner class ArrivalsTest {

        @Test
        internal fun testArrivals() {
            mockClient.stub {
                onBlocking { getArrivals(UIC_CODE) }.thenReturn(flowOf(readObject("arrival_item.json")))
            }

            val list = runBlocking { service.getArrivals(UIC_CODE).toList(mutableListOf()) }

            assertThat(list).hasSize(1)

            with(list[0]) {
                assertThat(origin).isEqualTo("Alphen a/d Rijn")
                assertThat(plannedArrival).isEqualInUtc("2019-10-25T21:02:00+0200")
                assertThat(actualArrival).isEqualInUtc("2019-10-25T21:02:00+0200")
                assertThat(plannedTrack).isEqualTo("11")
                assertThat(actualTrack).isEqualTo("11")
                assertThat(cancelled).isFalse()
                with(unit) {
                    assertThat(number).isEqualTo("R-net 8667")
                    assertThat(type).isEqualTo(TransportationType.TRAIN)
                    assertThat(category.code).isEqualTo("SPR")
                    assertThat(category.name).isEqualTo("Sprinter")
                    assertThat(operator).isEqualTo("R-net")
                }
            }
        }

        @Test
        internal fun testError() {
            mockClient.stub {
                onBlocking { getArrivals(UIC_CODE) }.thenThrow(IllegalStateException::class.java)
            }

            assertThatThrownBy { runBlocking { service.getArrivals(UIC_CODE) } }
                .isInstanceOf(IllegalStateException::class.java)
        }
    }
}