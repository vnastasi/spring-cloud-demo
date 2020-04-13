package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(builderClassName = "WrapperBuilder")
@JsonDeserialize(builder = TrackWrapper.WrapperBuilder.class)
public class TrackWrapper {

    @NonNull
    @JsonProperty("spoorNummer")
    String number;

    @JsonPOJOBuilder(withPrefix = "")
    static class WrapperBuilder {
    }
}
