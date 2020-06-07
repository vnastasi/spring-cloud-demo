package md.vnastasi.cloud.client.model.notification

import com.fasterxml.jackson.annotation.JsonProperty
import md.vnastasi.cloud.client.model.DisruptionTypeWrapper
import java.time.OffsetDateTime

data class NotificationWrapper(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("type")
    val type: DisruptionTypeWrapper,

    @JsonProperty("titel")
    val title: String,

    @JsonProperty("beschrijving")
    val description: String,

    @JsonProperty("url")
    val url: String,

    @JsonProperty("laatstGewijzigd")
    val lastUpdate: OffsetDateTime,

    @JsonProperty("volgendeUpdate")
    val nextUpdate: OffsetDateTime
)