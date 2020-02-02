package md.vnastasi.cloud.endpoint.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime

data class Departure(
    val destination: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val plannedDeparture: OffsetDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val actualDeparture: OffsetDateTime,
    val plannedTrack: String,
    val unit: TransportationUnit,
    val intermediateStations: List<IntermediateStation>,
    val status: DepartureStatus,
    val cancelled: Boolean,
    val messages: List<Message>
)
