package md.vnastasi.cloud.endpoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record Period(
        @JsonProperty("start")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime start,

        @JsonProperty("end")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        OffsetDateTime end) {
}
