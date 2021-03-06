package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record NamesWrapper(
    @JsonProperty("kort") String shortName,
    @JsonProperty("middel") String middleName,
    @JsonProperty("lang") String longName
) {
}
