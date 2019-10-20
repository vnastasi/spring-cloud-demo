package md.vnastasi.cloud.endpoint.model

import java.time.OffsetDateTime

data class Arrival(
    val origin: String,
    val plannedArrival: OffsetDateTime,
    val actualArrival: OffsetDateTime,
    val plannedTrack: String,
    val actualTrack: String,
    val unit: TransportationUnit,
    val cancelled: Boolean,
    val messages: List<Message>
)
