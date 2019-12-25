package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageWrapper(

    @JsonProperty("message")
    val text: String,

    @JsonProperty("style")
    val type: MessageTypeWrapper
)
