package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ArrivalsPayloadWrapper(

    @JsonProperty("arrivals")
    val arrivals: List<ArrivalWrapper>
)
