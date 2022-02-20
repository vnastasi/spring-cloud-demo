package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public record TimespanWrapper(
        @JsonProperty("start")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
        OffsetDateTime start,

        @JsonProperty("end")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
        OffsetDateTime end,

        @JsonProperty("situation")
        LabelWrapper situation,

        @JsonProperty("cause")
        LabelWrapper cause,

        @JsonProperty("additionalTravelTime")
        LabelWrapper additionalTravelTime,

        @JsonProperty("alternativeTransport")
        LabelWrapper alternativeTransport,

        @JsonProperty("advices")
        List<String> advices
) {
}
