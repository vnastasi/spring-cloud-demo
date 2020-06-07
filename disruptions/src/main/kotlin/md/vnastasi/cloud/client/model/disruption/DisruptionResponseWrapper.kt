package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class DisruptionResponseWrapper(

    @JsonProperty("payload")
    val payload: DisruptionPayloadWrapper
)
