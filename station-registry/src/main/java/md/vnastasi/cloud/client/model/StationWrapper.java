package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public final class StationWrapper {

    @NonNull
    @JsonProperty("code")
    private String code;

    @NonNull
    @JsonProperty("UICCode")
    private String uicCode;

    @NonNull
    @JsonProperty("EVACode")
    private String evaCode;

    @NonNull
    @JsonProperty("stationType")
    private StationTypeWrapper stationType;

    @NonNull
    @JsonProperty("namen")
    private NamesWrapper names;

    @NonNull
    @JsonProperty("synoniemen")
    private List<String> synonyms;

    @NonNull
    @JsonProperty("land")
    private String countryCode;

    @NonNull
    @JsonProperty("lat")
    private Double latitude;

    @NonNull
    @JsonProperty("lng")
    private Double longitude;

    @NonNull
    @JsonProperty("sporen")
    private List<TrackWrapper> tracks;

    @NonNull
    @JsonProperty("heeftFaciliteiten")
    private Boolean facilitiesAvailable;

    @NonNull
    @JsonProperty("heeftVertrektijden")
    private Boolean timeTableAvailable;

    @NonNull
    @JsonProperty("heeftReisassistentie")
    private Boolean travelAssistanceAvailable;

    @NonNull
    @JsonProperty("radius")
    private Integer radius;

    @NonNull
    @JsonProperty("naderenRadius")
    private Integer nearRadius;
}
