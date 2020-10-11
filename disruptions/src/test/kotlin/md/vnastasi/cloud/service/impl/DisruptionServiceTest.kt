package md.vnastasi.cloud.service.impl

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.client.model.disruption.DisruptionItemWrapper
import md.vnastasi.cloud.endpoint.model.SearchPeriod
import md.vnastasi.cloud.endpoint.model.disturbance.Disturbance
import md.vnastasi.cloud.endpoint.model.disturbance.DisturbanceType
import md.vnastasi.cloud.endpoint.model.notification.Notification
import md.vnastasi.cloud.util.JsonUtils.readObject
import md.vnastasi.cloud.util.isSameAs
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DisruptionServiceTest {

    private val mockClient = mock<PublicTravelInfoClient>()

    private val service = DisruptionServiceImpl(mockClient)

    @Nested
    inner class DisturbancesTest {

        @Test
        fun testError() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doThrow IllegalStateException()
            }

            assertThat { runBlocking { service.getDisturbances(SearchPeriod()) } }
                .isFailure()
                .isInstanceOf(IllegalStateException::class)
        }

        @Test
        fun testUnboundPeriod() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doReturn readObject<List<DisruptionItemWrapper>>("disturbances.json").asFlow()
            }

            val list = runBlocking { service.getDisturbances(SearchPeriod()).toList(mutableListOf()) }
            assertThat(list).hasSize(4)
            assertThat(list[0]).all {
                prop(Disturbance::id).isEqualTo("2020_THALYS_asd_paris_1_18okt")
                prop(Disturbance::type).isEqualTo(DisturbanceType.MAINTENANCE)
                prop(Disturbance::trajectory).isEqualTo("Amsterdam C - Brussel-Zuid/Midi - Marne-la-Vallee/Paris-Nord")
                prop(Disturbance::cause).isEqualTo("Aangepaste dienstregeling")
                prop(Disturbance::start).isSameAs("2020-10-01T04:00:00+0200".toOffsetDateTime())
                prop(Disturbance::end).isSameAs("2020-10-19T03:59:00+0200".toOffsetDateTime())
            }
            assertThat(list[1]).all {
                prop(Disturbance::id).isEqualTo("2020_hvs_amf_14-18okt")
                prop(Disturbance::type).isEqualTo(DisturbanceType.MAINTENANCE)
                prop(Disturbance::trajectory).isEqualTo("Hilversum-Amersfoort Centraal")
                prop(Disturbance::cause).isEqualTo("Werkzaamheden")
                prop(Disturbance::start).isSameAs("2020-10-14T01:00:00+0200".toOffsetDateTime())
                prop(Disturbance::end).isSameAs("2020-10-19T03:59:00+0200".toOffsetDateTime())
            }
            assertThat(list[2]).all {
                prop(Disturbance::id).isEqualTo("2020_wt_std_17_25okt")
                prop(Disturbance::type).isEqualTo(DisturbanceType.MAINTENANCE)
                prop(Disturbance::trajectory).isEqualTo("Weert-Sittard")
                prop(Disturbance::cause).isEqualTo("Werkzaamheden")
                prop(Disturbance::start).isSameAs("2020-10-17T04:00:00+0200".toOffsetDateTime())
                prop(Disturbance::end).isSameAs("2020-10-26T03:59:00+0100".toOffsetDateTime())
            }
            assertThat(list[3]).all {
                prop(Disturbance::id).isEqualTo("2020_ICBER_asd_berlin_27_29nov")
                prop(Disturbance::type).isEqualTo(DisturbanceType.MAINTENANCE)
                prop(Disturbance::trajectory).isEqualTo("Amsterdam C-Hannover Hbf- Berlin Ostbahnhof")
                prop(Disturbance::cause).isEqualTo("Werkzaamheden")
                prop(Disturbance::start).isSameAs("2020-11-27T04:00:00+0100".toOffsetDateTime())
                prop(Disturbance::end).isSameAs("2020-11-30T03:59:00+0100".toOffsetDateTime())
            }
        }

        @Test
        fun testStartPeriod() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doReturn readObject<List<DisruptionItemWrapper>>("disturbances.json").asFlow()
            }

            val period = SearchPeriod(start = "2020-10-17")
            val list = runBlocking { service.getDisturbances(period).toList(mutableListOf()) }
            assertThat(list).hasSize(2)
            assertThat(list[0]).prop(Disturbance::id).isEqualTo("2020_wt_std_17_25okt")
            assertThat(list[1]).prop(Disturbance::id).isEqualTo("2020_ICBER_asd_berlin_27_29nov")
        }

        @Test
        fun testEndPeriod() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doReturn readObject<List<DisruptionItemWrapper>>("disturbances.json").asFlow()
            }

            val period = SearchPeriod(start = "2020-10-15", end = "2020-10-20")
            val list = runBlocking { service.getDisturbances(period).toList(mutableListOf()) }
            assertThat(list).hasSize(1)
            assertThat(list[0]).prop(Disturbance::id).isEqualTo("2020_wt_std_17_25okt")
        }

        @Test
        fun testBoundPeriod() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doReturn readObject<List<DisruptionItemWrapper>>("disturbances.json").asFlow()
            }

            val period = SearchPeriod(end = "2020-10-17")
            val list = runBlocking { service.getDisturbances(period).toList(mutableListOf()) }
            assertThat(list).hasSize(2)
            assertThat(list[0]).prop(Disturbance::id).isEqualTo("2020_THALYS_asd_paris_1_18okt")
            assertThat(list[1]).prop(Disturbance::id).isEqualTo("2020_hvs_amf_14-18okt")
        }

        @Test
        fun testNoDisturbances() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doReturn readObject<List<DisruptionItemWrapper>>("empty_list.json").asFlow()
            }

            val list = runBlocking { service.getDisturbances(SearchPeriod()).toList(mutableListOf()) }
            assertThat(list).isEmpty()
        }
    }

    @Nested
    inner class NotificationsTest {

        @Test
        fun testNotifications() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doReturn readObject<List<DisruptionItemWrapper>>("disturbances_and_notifications.json").asFlow()
            }

            val list = runBlocking { service.getNotifications().toList(mutableListOf()) }
            assertThat(list).hasSize(1)
            assertThat(list[0]).all {
                prop(Notification::id).isEqualTo("00000")
                prop(Notification::title).isEqualTo("COVID-19")
                prop(Notification::level).isEqualTo(1)
                prop(Notification::description).isEqualTo("Coronavirus update")
                prop(Notification::infoUrl).isEqualTo("https://www.google.com")
                prop(Notification::lastUpdate).isSameAs("2020-03-15T15:00:00+0200".toOffsetDateTime())
                prop(Notification::nextUpdate).isSameAs("2020-12-31T23:00:00+0200".toOffsetDateTime())
            }
        }

        @Test
        fun testNoNotifications() {
            mockClient.stub {
                onBlocking { getAllDisruptions() } doReturn readObject<List<DisruptionItemWrapper>>("disturbances.json").asFlow()
            }

            val list = runBlocking { service.getNotifications().toList(mutableListOf()) }
            assertThat(list).isEmpty()
        }
    }

    private fun String.toOffsetDateTime(): OffsetDateTime =
        OffsetDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ").withZone(ZoneOffset.UTC))
}
