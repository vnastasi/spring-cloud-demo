package md.vnastasi.cloud.endpoint.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime

data class Notification(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val lastUpdate: OffsetDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val nextUpdate: OffsetDateTime?
)
