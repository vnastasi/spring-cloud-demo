package md.vnastasi.cloud.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Interval(
        @JsonProperty("period")
        Period period,

        @JsonProperty("description")
        String description,

        @JsonProperty("cause")
        String cause,

        @JsonProperty("extraTravelTimeAdvice")
        String extraTravelTimeAdvice,

        @JsonProperty("alternativeTransportAdvice")
        String alternativeTransportAdvice,

        @JsonProperty("otherAdvices")
        List<String> otherAdvices
) {
}
