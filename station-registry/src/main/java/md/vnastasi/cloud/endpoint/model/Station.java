package md.vnastasi.cloud.endpoint.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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
