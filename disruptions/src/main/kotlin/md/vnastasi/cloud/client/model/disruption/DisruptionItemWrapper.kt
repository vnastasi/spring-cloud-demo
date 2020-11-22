package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

data class DisruptionItemWrapper(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("titel")
    val title: String,

    @JsonProperty("melding")
    val notification: NotificationWrapper?,

    @JsonProperty("verstoring")
    val disturbance: DisturbanceWrapper?
)
