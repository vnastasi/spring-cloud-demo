package md.vnastasi.cloud.endpoint.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime

data class Maintenance(
    val id: String,
    val header: String,
    val consequence: String,
    val extraTravelTime: String,
    val travelAdvice: TravelAdvice,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val start: OffsetDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val end: OffsetDateTime
)