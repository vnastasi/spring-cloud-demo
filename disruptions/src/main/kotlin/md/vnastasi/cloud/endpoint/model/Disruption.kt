package md.vnastasi.cloud.endpoint.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime

data class Disruption(
    val id: String,
    val type: DisruptionType,
    val summary: String,
    val consequence: String,
    val travelAdvice: TravelAdviceHolder,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val start: OffsetDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val end: OffsetDateTime
)
