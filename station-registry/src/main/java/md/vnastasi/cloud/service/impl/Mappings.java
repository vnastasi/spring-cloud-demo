package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.model.NamesWrapper;
import md.vnastasi.cloud.client.model.StationTypeWrapper;
import md.vnastasi.cloud.client.model.StationWrapper;
import md.vnastasi.cloud.client.model.TrackWrapper;
import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.NameHolder;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.endpoint.model.StationType;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

final class Mappings {

    private Mappings() { }

    @NonNull
    static Station map(@NonNull StationWrapper input) {
        var coordinates = new Coordinates(input.latitude(), input.longitude());
        return new Station(
            input.uicCode(),
            map(input.names()),
            map(input.stationType()),
            List.copyOf(input.synonyms()),
            input.countryCode(),
            map(input.tracks()),
            coordinates
        );
    }

    @NonNull
    private static NameHolder map(@NonNull NamesWrapper input) {
        return new NameHolder(
            Optional.ofNullable(input.shortName()).orElse(""),
            Optional.ofNullable(input.middleName()).orElse(""),
            Optional.ofNullable(input.longName()).orElse("")
        );
    }

    @NonNull
    private static StationType map(@NonNull StationTypeWrapper input) {
        return switch (input) {
            case MAJOR_STATION -> StationType.MAJOR_STATION;
            case INTERCITY_JUNCTION_STATION, FAST_TRAIN_JUNCTION_STATION -> StationType.INTERCITY_JUNCTION_STATION;
            case INTERCITY_STATION, FAST_TRAIN_STATION -> StationType.INTERCITY_STATION;
            case STOP_TRAIN_STATION -> StationType.STOP_TRAIN_STATION;
            case STOP_TRAIN_JUNCTION_STATION -> StationType.STOP_TRAIN_JUNCTION_STATION;
            case OPTIONAL_STATION -> StationType.OPTIONAL_STATION;
        };
    }

    @NonNull
    private static List<String> map(@NonNull List<TrackWrapper> input) {
        return input.stream().map(TrackWrapper::number).toList();
    }
}
