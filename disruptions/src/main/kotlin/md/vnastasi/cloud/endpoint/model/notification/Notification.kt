package md.vnastasi.cloud.endpoint.model.notification

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Notification(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("description")
    val description: String,

    @JsonProperty("priority")
    val level: Int,

    @JsonProperty("lastUpdate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val lastUpdate: OffsetDateTime,

    @JsonProperty("nextUpdate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val nextUpdate: OffsetDateTime,

    @JsonProperty("infoUrl")
    val infoUrl: String
)
