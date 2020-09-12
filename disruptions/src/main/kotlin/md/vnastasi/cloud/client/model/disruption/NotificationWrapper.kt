package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class NotificationWrapper(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("titel")
    val title: String,

    @JsonProperty("beschrijving")
    val description: String,

    @JsonProperty("url")
    val url: String,

    @JsonProperty("type")
    val type: NotificationTypeWrapper,

    @JsonProperty("laatstGewijzigd")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val lastUpdate: OffsetDateTime,

    @JsonProperty("volgendeUpdate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val nextUpdate: OffsetDateTime
)
