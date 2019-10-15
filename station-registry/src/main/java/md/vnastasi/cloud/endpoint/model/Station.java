package md.vnastasi.cloud.endpoint.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public final class Station {

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
