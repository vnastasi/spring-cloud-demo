package md.vnastasi.cloud.service.impl

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.endpoint.model.*
import md.vnastasi.cloud.util.JsonUtils.readObject
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import md.vnastasi.cloud.util.isEqualTo

private const val UIC_CODE = "34567"

class TimetableServiceTest {

    private val mockClient = mock<PublicTravelInfoClient>()

    private val service = TimetableServiceImpl(mockClient)

    @Nested
    inner class DeparturesTest {

        @Test
        internal fun testDepartures() = runTest {
            whenever(mockClient.getDepartures(UIC_CODE)).doReturn(flowOf(readObject("departure_item.json")))

            val list = service.getDepartures(UIC_CODE).toList(mutableListOf())

            assertThat(list).hasSize(1)

            assertThat(list[0]).all {
                prop(Departure::destination).isEqualTo("Den Haag Centraal")
                prop(Departure::plannedDeparture).isEqualTo("2019-10-25T19:02:00+0200")
                prop(Departure::actualDeparture).isEqualTo("2019-10-25T19:02:00+0200")
                prop(Departure::plannedTrack).isEqualTo("8")
                prop(Departure::cancelled).isFalse()
                prop(Departure::intermediateStations).isEmpty()
                prop(Departure::messages).isEmpty()
                prop(Departure::status).isEqualTo(DepartureStatus.BOARDING)
                prop(Departure::unit).all {
                    prop(TransportationUnit::number).isEqualTo("NS 2062")
                    prop(TransportationUnit::type).isEqualTo(TransportationType.TRAIN)
                    prop(TransportationUnit::category).isDataClassEqualTo(Category(code = "IC", name = "Intercity"))
                    prop(TransportationUnit::operator).isEqualTo("NS")
                }
            }
        }

        @Test
        internal fun testError() = runTest {
            whenever(mockClient.getDepartures(UIC_CODE)).doThrow(IllegalStateException())

            assertThat { service.getDepartures(UIC_CODE) }
                .isFailure()
                .isInstanceOf(IllegalStateException::class)
        }
    }

    @Nested
    inner class ArrivalsTest {

        @Test
        internal fun testArrivals() = runTest {
            whenever(mockClient.getArrivals(UIC_CODE)).doReturn(flowOf(readObject("arrival_item.json")))

            val list = service.getArrivals(UIC_CODE).toList(mutableListOf())

            assertThat(list).hasSize(1)

            assertThat(list[0]).all {
                prop(Arrival::origin).isEqualTo("Alphen a/d Rijn")
                prop(Arrival::plannedArrival).isEqualTo("2019-10-25T21:02:00+0200")
                prop(Arrival::actualArrival).isEqualTo("2019-10-25T21:02:00+0200")
                prop(Arrival::plannedTrack).isEqualTo("11")
                prop(Arrival::actualTrack).isEqualTo("11")
                prop(Arrival::cancelled).isFalse()
                prop(Arrival::unit).all {
                    prop(TransportationUnit::number).isEqualTo("R-net 8667")
                    prop(TransportationUnit::type).isEqualTo(TransportationType.TRAIN)
                    prop(TransportationUnit::category).isDataClassEqualTo(Category(code = "SPR", name = "Sprinter"))
                    prop(TransportationUnit::operator).isEqualTo("R-net")
                }
            }
        }

        @Test
        internal fun testError() = runTest {
            whenever(mockClient.getArrivals(UIC_CODE)).doThrow(IllegalStateException())

            assertThat { service.getArrivals(UIC_CODE) }
                .isFailure()
                .isInstanceOf(IllegalStateException::class)
        }
    }
}
