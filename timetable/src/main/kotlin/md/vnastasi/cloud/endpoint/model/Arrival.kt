package md.vnastasi.cloud.endpoint.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime

data class Arrival(
    val origin: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val plannedArrival: OffsetDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val actualArrival: OffsetDateTime,
    val plannedTrack: String,
    val actualTrack: String,
    val unit: TransportationUnit,
    val cancelled: Boolean,
    val messages: List<Message>
)
