package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class SectionWrapper(

    @JsonProperty("stations")
    val stations: List<String>,

    @JsonProperty("begintijd")
    val startDate: List<String>,

    @JsonProperty("eindtijd")
    val endDate: List<String>
)
