package md.vnastasi.cloud.client.model.error

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorWrapper(

    @JsonProperty("code")
    val code: Int,

    @JsonProperty("type")
    val type: ErrorTypeWrapper,

    @JsonProperty("message")
    val message: String
)
