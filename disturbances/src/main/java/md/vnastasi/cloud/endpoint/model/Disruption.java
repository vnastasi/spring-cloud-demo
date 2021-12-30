package md.vnastasi.cloud.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Disruption(
        @JsonProperty("id")
        String id,

        @JsonProperty("title")
        String title,

        @JsonProperty("type")
        DisturbanceType type,

        @JsonProperty("period")
        Period period,

        @JsonProperty("intervals")
        List<Interval> intervals,

        @JsonProperty("isActive")
        boolean isActive
) {
}
