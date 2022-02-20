package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record NamesWrapper(
        @Nullable
        @JsonProperty("kort")
        String shortName,

        @Nullable
        @JsonProperty("middel")
        String middleName,

        @Nullable
        @JsonProperty("lang")
        String longName
) {
}
