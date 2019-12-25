package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder(builderClassName = "WrapperBuilder")
@JsonDeserialize(builder = StationsResponseWrapper.WrapperBuilder.class)
public class StationsResponseWrapper {

    @NonNull
    @JsonProperty("payload")
    private List<StationWrapper> payload;

    @JsonPOJOBuilder(withPrefix = "")
    static class WrapperBuilder {
    }
}
