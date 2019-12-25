package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class DeparturesResponseWrapper(

    @JsonProperty("payload")
    val payload: DeparturesPayloadWrapper
)
