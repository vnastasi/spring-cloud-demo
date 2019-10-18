package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public final class StationsResponseWrapper {

    @NonNull
    @JsonProperty("payload")
    private List<StationWrapper> payload;
}
