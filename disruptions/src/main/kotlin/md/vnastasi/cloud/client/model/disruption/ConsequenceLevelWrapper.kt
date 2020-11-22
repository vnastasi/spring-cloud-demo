package md.vnastasi.cloud.client.model.disruption

import com.fasterxml.jackson.annotation.JsonProperty

enum class ConsequenceLevelWrapper {

    @JsonProperty("GEEN_OF_VEEL_MINDER_TREINEN")
    NONE_OR_MUCH_LESS_TRAINS,

    @JsonProperty("MINDER_TREINEN")
    LESS_TRAINS,

    @JsonProperty("NORMAAL_AANTAL_OF_MEER_TREINEN")
    USUAL_OR_MORE_TRAINS
}
