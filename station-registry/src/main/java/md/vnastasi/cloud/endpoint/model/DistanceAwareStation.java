package md.vnastasi.cloud.endpoint.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record DistanceAwareStation(
    @JsonProperty Station station,
    @JsonProperty double distance
) {
}
