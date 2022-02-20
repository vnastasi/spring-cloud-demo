package md.vnastasi.cloud.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DisturbanceType {

    @JsonProperty("CALAMITY")
    CALAMITY,

    @JsonProperty("MAINTENANCE")
    MAINTENANCE,

    @JsonProperty("DISRUPTION")
    DISRUPTION
}
