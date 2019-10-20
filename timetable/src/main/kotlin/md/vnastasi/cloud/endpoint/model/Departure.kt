package md.vnastasi.cloud.endpoint.model

import java.time.OffsetDateTime

data class Departure(
    val destination: String,
    val plannedDeparture: OffsetDateTime,
    val actualDeparture: OffsetDateTime,
    val plannedTrack: String,
    val unit: TransportationUnit,
    val routeStations: List<RouteStation>,
    val status: DepartureStatus,
    val cancelled: Boolean,
    val messages: List<Message>
)
