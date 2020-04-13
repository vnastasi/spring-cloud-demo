package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder(builderClassName = "WrapperBuilder")
@JsonDeserialize(builder = StationWrapper.WrapperBuilder.class)
public class StationWrapper {

    @NonNull
    @JsonProperty("code")
    String code;

    @NonNull
    @JsonProperty("UICCode")
    String uicCode;

    @NonNull
    @JsonProperty("EVACode")
    String evaCode;

    @NonNull
    @JsonProperty("stationType")
    StationTypeWrapper stationType;

    @NonNull
    @JsonProperty("namen")
    NamesWrapper names;

    @NonNull
    @JsonProperty("synoniemen")
    List<String> synonyms;

    @NonNull
    @JsonProperty("land")
    String countryCode;

    @NonNull
    @JsonProperty("lat")
    Double latitude;

    @NonNull
    @JsonProperty("lng")
    Double longitude;

    @NonNull
    @JsonProperty("sporen")
    List<TrackWrapper> tracks;

    @NonNull
    @JsonProperty("heeftFaciliteiten")
    Boolean facilitiesAvailable;

    @NonNull
    @JsonProperty("heeftVertrektijden")
    Boolean timeTableAvailable;

    @NonNull
    @JsonProperty("heeftReisassistentie")
    Boolean travelAssistanceAvailable;

    @NonNull
    @JsonProperty("radius")
    Integer radius;

    @NonNull
    @JsonProperty("naderenRadius")
    Integer nearRadius;

    @JsonPOJOBuilder(withPrefix = "")
    static class WrapperBuilder {
    }
}
