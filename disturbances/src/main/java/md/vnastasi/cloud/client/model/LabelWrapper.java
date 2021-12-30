package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LabelWrapper(
        @JsonProperty("label")
        String label
) {
}
