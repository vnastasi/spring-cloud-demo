package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StationsResponseWrapper(
        @JsonProperty("payload")
        List<StationWrapper> payload
) {
}
