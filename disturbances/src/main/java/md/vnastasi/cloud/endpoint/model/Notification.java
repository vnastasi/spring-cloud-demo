package md.vnastasi.cloud.endpoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record Notification(
        @JsonProperty("id")
        String id,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("lastUpdated")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime lastUpdated,

        @JsonProperty("infoUrl")
        String infoUrl
) {
}
