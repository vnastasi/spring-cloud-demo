package md.vnastasi.cloud.endpoint.model;

import java.util.List;

public record Station(
        String code,
        NameHolder names,
        StationType type,
        List<String> synonyms,
        String countryCode,
        List<String> tracks,
        Coordinates coordinates
) {
}
