package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ArrivalsResponseWrapper(

    @JsonProperty("payload")
    val payload: ArrivalsPayloadWrapper
)
