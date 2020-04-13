package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "WrapperBuilder")
@JsonDeserialize(builder = NamesWrapper.WrapperBuilder.class)
public class NamesWrapper {

    @JsonProperty("kort")
    String shortName;

    @JsonProperty("middel")
    String middleName;

    @JsonProperty("lang")
    String longName;

    @JsonPOJOBuilder(withPrefix = "")
    static class WrapperBuilder {
    }
}
