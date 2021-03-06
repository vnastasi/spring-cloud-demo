package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StationTypeWrapper {

    @JsonProperty("STOPTREIN_STATION")
    STOP_TRAIN_STATION,

    @JsonProperty("KNOOPPUNT_STOPTREIN_STATION")
    STOP_TRAIN_JUNCTION_STATION,

    @JsonProperty("SNELTREIN_STATION")
    FAST_TRAIN_STATION,

    @JsonProperty("KNOOPPUNT_SNELTREIN_STATION")
    FAST_TRAIN_JUNCTION_STATION,

    @JsonProperty("INTERCITY_STATION")
    INTERCITY_STATION,

    @JsonProperty("KNOOPPUNT_INTERCITY_STATION")
    INTERCITY_JUNCTION_STATION,

    @JsonProperty("MEGA_STATION")
    MAJOR_STATION,

    @JsonProperty("FACULTATIEF_STATION")
    OPTIONAL_STATION
}
