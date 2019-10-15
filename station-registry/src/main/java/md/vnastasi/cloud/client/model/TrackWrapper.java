package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public final class TrackWrapper {

    @NonNull
    @JsonProperty("spoorNummer")
    private String number;
}
