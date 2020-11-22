package md.vnastasi.cloud.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import md.vnastasi.cloud.client.PublicTravelInfoClient
import md.vnastasi.cloud.endpoint.model.SearchPeriod
import md.vnastasi.cloud.endpoint.model.disturbance.Disturbance
import md.vnastasi.cloud.endpoint.model.notification.Notification
import md.vnastasi.cloud.service.DisruptionService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Service
class DisruptionServiceImpl(
    private val client: PublicTravelInfoClient
) : DisruptionService {

    override suspend fun getNotifications(): Flow<Notification> =
        client.getAllDisruptions()
            .mapNotNull { it.notification }
            .map { it.asNotification() }

    override suspend fun getDisturbances(period: SearchPeriod): Flow<Disturbance> {
        val start = period.start?.let(::convertDate) ?: epochStart()
        val end = period.end?.let(::convertDate) ?: epochEnd()
        return client.getAllDisruptions()
            .mapNotNull { it.disturbance }
            .map { it.asDisturbance() }
            .filter { it.start.isAfter(start) && it.start.isBefore(end) }
    }

    private fun convertDate(input: String): OffsetDateTime {
        val localDate = LocalDate.parse(input)
        return OffsetDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneOffset.UTC)
    }
}
