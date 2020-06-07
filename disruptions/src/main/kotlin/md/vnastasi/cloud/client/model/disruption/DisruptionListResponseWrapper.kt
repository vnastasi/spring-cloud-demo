package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty
import md.vnastasi.cloud.client.model.BaseDisruptionWrapper

data class DisruptionListResponseWrapper(

    @JsonProperty("payload")
    val payload: List<BaseDisruptionWrapper>
)
