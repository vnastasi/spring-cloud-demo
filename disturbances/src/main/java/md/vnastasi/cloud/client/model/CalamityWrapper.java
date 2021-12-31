package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
        OffsetDateTime lastUpdated,

        @JsonProperty("url")
        String url,

        @JsonProperty("isActive")
        boolean isActive
) implements DisturbanceWrapper {
}
