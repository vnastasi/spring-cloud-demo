package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class TravelAdviceItemWrapper(

    @JsonProperty("titel")
    val title: String,

    @JsonProperty("advies")
    val advice: List<String>
)
