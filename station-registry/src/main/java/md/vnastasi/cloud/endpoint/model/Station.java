package md.vnastasi.cloud.endpoint.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Station {

    @NonNull
    private String code;

    @NonNull
    private NameHolder names;

    @NonNull
    private StationType type;

    @NonNull
    private List<String> synonyms;

    @NonNull
    private String countryCode;

    @NonNull
    private List<String> tracks;

    @NonNull
    private Coordinates coordinates;
}
