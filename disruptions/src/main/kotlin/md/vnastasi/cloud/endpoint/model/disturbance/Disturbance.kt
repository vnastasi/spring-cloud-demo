package md.vnastasi.cloud.endpoint.model.disturbance

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Disturbance(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("type")
    val type: DisturbanceType,

    @JsonProperty("trajectory")
    val trajectory: String,

    @JsonProperty("start")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val start: OffsetDateTime,

    @JsonProperty("end")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val end: OffsetDateTime,

    @JsonProperty("cause")
    val cause: String
)
