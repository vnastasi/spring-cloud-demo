package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class TrajectoryWrapper(

    @JsonProperty("stations")
    val stations: List<String>,

    @JsonProperty("begintijd")
    val startTime: OffsetDateTime,

    @JsonProperty("eindtijd")
    val endTime: OffsetDateTime,

    @JsonProperty("richting")
    val direction: DirectionWrapper?,

    @JsonProperty("gevolg")
    val consequence: ConsequenceWrapper?
)
