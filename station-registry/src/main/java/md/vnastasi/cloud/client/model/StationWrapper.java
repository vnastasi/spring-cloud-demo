package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StationWrapper(
        @JsonProperty("code")
        String code,

        @JsonProperty("UICCode")
        String uicCode,

        @JsonProperty("EVACode")
        String evaCode,

        @JsonProperty("stationType")
        StationTypeWrapper stationType,

        @JsonProperty("namen")
        NamesWrapper names,

        @JsonProperty("synoniemen")
        List<String> synonyms,

        @JsonProperty("land")
        String countryCode,

        @JsonProperty("lat")
        Double latitude,

        @JsonProperty("lng")
        Double longitude,

        @JsonProperty("sporen")
        List<TrackWrapper> tracks,

        @JsonProperty("heeftFaciliteiten")
        Boolean facilitiesAvailable,

        @JsonProperty("heeftVertrektijden")
        Boolean timeTableAvailable,

        @JsonProperty("heeftReisassistentie")
        Boolean travelAssistanceAvailable,

        @JsonProperty("radius")
        Integer radius,

        @JsonProperty("naderenRadius")
        Integer nearRadius
) {
}
