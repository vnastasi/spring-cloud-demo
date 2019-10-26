package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class ArrivalWrapper(

    @JsonProperty("origin")
    val origin: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("plannedTrack")
    val plannedTrack: String,

    @JsonProperty("actualTrack")
    val actualTrack: String,

    @JsonProperty("product")
    val product: ProductWrapper,

    @JsonProperty("trainCategory")
    val trainCategory: String,

    @JsonProperty("cancelled")
    val cancelled: Boolean,

    @JsonProperty("plannedDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    val plannedDateTime: OffsetDateTime,

    @JsonProperty("actualDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    val actualDateTime: OffsetDateTime,

    @JsonProperty("messages")
    val messages: List<MessageWrapper>?
)
