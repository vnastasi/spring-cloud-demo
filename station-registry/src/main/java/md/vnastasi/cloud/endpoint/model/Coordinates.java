package md.vnastasi.cloud.endpoint.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Coordinates {

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;
}
