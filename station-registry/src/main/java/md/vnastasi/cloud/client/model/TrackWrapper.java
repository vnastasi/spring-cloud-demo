package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class TrackWrapper {

    @NonNull
    @JsonProperty("spoorNummer")
    private String number;
}
