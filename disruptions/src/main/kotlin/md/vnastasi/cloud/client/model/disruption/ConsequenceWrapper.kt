package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class ConsequenceWrapper(

    @JsonProperty("stations")
    val stations: List<String>?,

    @JsonProperty("niveau")
    val level: ConsequenceLevelWrapper?
)
