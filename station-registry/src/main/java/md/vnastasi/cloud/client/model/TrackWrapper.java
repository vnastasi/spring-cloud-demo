package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrackWrapper(
        @JsonProperty("spoorNummer")
        String number
) {
}
