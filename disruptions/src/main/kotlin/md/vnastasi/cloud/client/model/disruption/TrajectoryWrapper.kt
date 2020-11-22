package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class TrajectoryWrapper(

    @JsonProperty("stations")
    val stations: List<String>,

    @JsonProperty("begintijd")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val startTime: OffsetDateTime,

    @JsonProperty("eindtijd")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    val endTime: OffsetDateTime,

    @JsonProperty("richting")
    val direction: DirectionWrapper?,

    @JsonProperty("gevolg")
    val consequence: ConsequenceWrapper?
)
