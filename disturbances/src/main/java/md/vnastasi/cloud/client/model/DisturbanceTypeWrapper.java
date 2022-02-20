package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DisturbanceTypeWrapper {

    @JsonProperty("CALAMITY")
    CALAMITY,

    @JsonProperty("DISRUPTION")
    DISRUPTION,

    @JsonProperty("MAINTENANCE")
    MAINTENANCE
}
