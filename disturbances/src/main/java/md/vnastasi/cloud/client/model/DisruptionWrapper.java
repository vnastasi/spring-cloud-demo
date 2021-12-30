package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public record DisruptionWrapper(
        @JsonProperty("id")
        String id,

        @JsonProperty("type")
        DisturbanceTypeWrapper type,

        @JsonProperty("title")
        String title,

        @JsonProperty("isActive")
        boolean isActive,

        @JsonProperty("start")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
        OffsetDateTime start,

        @JsonProperty("end")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
        OffsetDateTime end,

        @JsonProperty("timespans")
        List<TimespanWrapper> timeSpans
) implements DisturbanceWrapper {
}
