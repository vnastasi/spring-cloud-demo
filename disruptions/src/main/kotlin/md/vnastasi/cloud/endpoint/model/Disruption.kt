package md.vnastasi.cloud.endpoint.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime

data class Disruption(
    val id: String,
    val summary: String,
    val cause: String,
    val expectation: String,
    val alternativeTransport: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val reportedAt: OffsetDateTime
)
