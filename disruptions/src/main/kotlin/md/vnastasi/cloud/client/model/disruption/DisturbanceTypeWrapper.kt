package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

enum class DisturbanceTypeWrapper {

    @JsonProperty("MELDING_PRIO_1")
    PRIORITY_1,

    @JsonProperty("MELDING_PRIO_2")
    PRIORITY_2,

    @JsonProperty("MELDING_PRIO_2")
    PRIORITY_3,

    @JsonProperty("STORING")
    DISRUPTION,

    @JsonProperty("WERKZAAMHEID")
    MAINTENANCE,

    @JsonProperty("EVENEMENT")
    EVENT
}
