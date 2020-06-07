package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import md.vnastasi.cloud.client.model.BaseDisruptionWrapper

@JsonDeserialize(using = JsonDeserializer.None::class)
data class DisruptionPayloadWrapper(

    @JsonProperty("id")
    override val id: String,

    @JsonProperty("type")
    override val type: String,

    @JsonProperty("titel")
    override val title: String,

    @JsonProperty(MARKER_PROPERTY)
    val disruption: DisruptionWrapper

) : BaseDisruptionWrapper {

    companion object {
        const val MARKER_PROPERTY = "verstoring"
    }
}
