package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class TravelAdviceListWrapper(

    @JsonProperty("titel")
    val title: String,

    @JsonProperty("reisadvies")
    val travelAdvices: List<TravelAdviceWrapper>
)
