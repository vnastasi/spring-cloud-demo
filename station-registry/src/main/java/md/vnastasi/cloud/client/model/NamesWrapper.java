package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class NamesWrapper {

    @JsonProperty("kort")
    private String shortName;

    @JsonProperty("middel")
    private String middleName;

    @JsonProperty("lang")
    private String longName;
}
