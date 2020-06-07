package md.vnastasi.cloud.client.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class DisruptionTypeWrapper {

    @JsonProperty("prio_1")
    PRIORITY_1_NOTIFICATION,

    @JsonProperty("prio_2")
    PRIORITY_2_NOTIFICATION,

    @JsonProperty("prio_3")
    PRIORITY_3_NOTIFICATION,

    @JsonProperty("STORING")
    DISTURBANCE,

    @JsonProperty("WERKZAAMHEID")
    MAINTENANCE,

    @JsonProperty("EVENEMENT")
    EVENT
}
