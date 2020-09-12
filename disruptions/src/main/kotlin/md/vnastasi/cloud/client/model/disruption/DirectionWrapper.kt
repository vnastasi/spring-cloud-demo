package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

enum class DirectionWrapper {

    @JsonProperty("VANUIT")
    FROM,

    @JsonProperty("NAAR")
    TO,

    @JsonProperty("VANUIT_EN_NAAR")
    FROM_AND_TO,

    @JsonProperty("HEEN")
    ONE_DIRECTION,

    @JsonProperty("HEEN_EN_TERUG")
    BACK_AND_FORTH
}
