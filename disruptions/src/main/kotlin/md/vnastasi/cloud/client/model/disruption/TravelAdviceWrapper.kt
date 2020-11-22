package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class TravelAdviceWrapper(

    @JsonProperty("titel")
    val title: String,

    @JsonProperty("reisadvies")
    val travelAdvice: List<TravelAdviceItemWrapper>
)
