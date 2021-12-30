package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record CalamityWrapper(
        @JsonProperty("id")
        String id,

        @JsonProperty("type")
        DisturbanceTypeWrapper type,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("lastUpdated")
        OffsetDateTime lastUpdated,

        @JsonProperty("url")
        String url,

        @JsonProperty("isActive")
        boolean isActive
) implements DisturbanceWrapper {
}
