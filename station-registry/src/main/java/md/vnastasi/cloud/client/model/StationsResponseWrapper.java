package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public final class StationsResponseWrapper {

    @NonNull
    @JsonProperty("payload")
    private List<StationWrapper> payload;
}
