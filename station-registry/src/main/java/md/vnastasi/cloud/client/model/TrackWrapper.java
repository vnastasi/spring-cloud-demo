package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TrackWrapper(
    @JsonProperty("spoorNummer") String number
) {
}
