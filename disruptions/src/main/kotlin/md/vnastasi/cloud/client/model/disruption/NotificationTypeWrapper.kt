package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

enum class NotificationTypeWrapper {

    @JsonProperty("prio_1")
    PRIORITY_1,

    @JsonProperty("prio_2")
    PRIORITY_2,

    @JsonProperty("prio_3")
    PRIORITY_3
}
