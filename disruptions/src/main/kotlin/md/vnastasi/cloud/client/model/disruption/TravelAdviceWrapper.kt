package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class TravelAdviceWrapper(

    @JsonProperty("advies")
    val advice: List<String>
)
