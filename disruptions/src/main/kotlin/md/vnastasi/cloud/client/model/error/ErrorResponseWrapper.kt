package md.vnastasi.cloud.client.model.error

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponseWrapper(

    @JsonProperty("code")
    val httpStatusCode: Int,

    @JsonProperty("message")
    val httpStatusMessage: String,

    @JsonProperty("errors")
    val errors: List<ErrorWrapper>
)
