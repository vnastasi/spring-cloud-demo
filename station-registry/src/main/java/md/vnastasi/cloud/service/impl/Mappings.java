package md.vnastasi.cloud.service.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import md.vnastasi.cloud.client.model.NamesWrapper;
import md.vnastasi.cloud.client.model.StationTypeWrapper;
import md.vnastasi.cloud.client.model.StationWrapper;
import md.vnastasi.cloud.client.model.TrackWrapper;
import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.NameHolder;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.endpoint.model.StationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Mappings {

    @NonNull
    static Station map(@NonNull StationWrapper input) {
        var coordinates = Coordinates.builder().latitude(input.getLatitude()).longitude(input.getLongitude()).build();
        return Station.builder()
                .code(input.getUicCode())
                .names(map(input.getNames()))
                .type(map(input.getStationType()))
                .synonyms(new ArrayList<>(input.getSynonyms()))
                .countryCode(input.getCountryCode())
                .tracks(map(input.getTracks()))
                .coordinates(coordinates)
                .build();
    }

    @NonNull
    private static NameHolder map(@NonNull NamesWrapper input) {
        return NameHolder.builder()
                .shortName(Optional.ofNullable(input.getShortName()).orElse(""))
                .middleName(Optional.ofNullable(input.getMiddleName()).orElse(""))
                .longName(Optional.ofNullable(input.getLongName()).orElse(""))
                .build();
    }

    @NonNull
    private static StationType map(@NonNull StationTypeWrapper input) {
        switch (input) {
            case MAJOR_STATION:
                return StationType.MAJOR_STATION;
            case INTERCITY_JUNCTION_STATION:
            case FAST_TRAIN_JUNCTION_STATION:
                return StationType.INTERCITY_JUNCTION_STATION;
            case INTERCITY_STATION:
            case FAST_TRAIN_STATION:
                return StationType.INTERCITY_STATION;
            case STOP_TRAIN_STATION:
                return StationType.STOP_TRAIN_STATION;
            case STOP_TRAIN_JUNCTION_STATION:
                return StationType.STOP_TRAIN_JUNCTION_STATION;
            case OPTIONAL_STATION:
                return StationType.OPTIONAL_STATION;
            default:
                return StationType.UNKNOWN;
        }
    }

    @NonNull
    private static List<String> map(@NonNull List<TrackWrapper> input) {
        return input.stream().map(TrackWrapper::getNumber).collect(toList());
    }
}
