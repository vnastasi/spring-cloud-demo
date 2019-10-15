package md.vnastasi.cloud.endpoint.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class Coordinates {

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;
}
