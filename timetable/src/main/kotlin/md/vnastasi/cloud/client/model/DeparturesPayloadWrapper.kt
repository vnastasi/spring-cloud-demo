package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class DeparturesPayloadWrapper(

    @JsonProperty("departures")
    val departures: List<DepartureWrapper>
)
