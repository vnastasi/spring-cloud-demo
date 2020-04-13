package md.vnastasi.cloud.endpoint.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Station {

    @NonNull
    String code;

    @NonNull
    NameHolder names;

    @NonNull
    StationType type;

    @NonNull
    List<String> synonyms;

    @NonNull
    String countryCode;

    @NonNull
    List<String> tracks;

    @NonNull
    Coordinates coordinates;
}
