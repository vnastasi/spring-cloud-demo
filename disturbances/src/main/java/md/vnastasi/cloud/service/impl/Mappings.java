package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.model.*;
import md.vnastasi.cloud.endpoint.model.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

final class Mappings {

    private Mappings() {}

    @NonNull
    static Notification map(CalamityWrapper calamityWrapper) {
        return new Notification(
                calamityWrapper.id(),
                calamityWrapper.title(),
                calamityWrapper.description(),
                calamityWrapper.lastUpdated(),
                calamityWrapper.url()
        );
    }

    static Disruption map(@NonNull DisruptionWrapper disruptionWrapper) {
        return new Disruption(
                disruptionWrapper.id(),
                disruptionWrapper.title(),
                map(disruptionWrapper.type()),
                new Period(disruptionWrapper.start(), disruptionWrapper.end()),
                disruptionWrapper.timeSpans().stream().map(Mappings::map).collect(Collectors.toList()),
                disruptionWrapper.isActive()
        );
    }

    @NonNull
    static DisturbanceType map(@NonNull DisturbanceTypeWrapper disturbanceTypeWrapper) {
        return switch (disturbanceTypeWrapper) {
            case DISRUPTION -> DisturbanceType.DISRUPTION;
            case MAINTENANCE -> DisturbanceType.MAINTENANCE;
            case CALAMITY -> DisturbanceType.CALAMITY;
        };
    }

    @NonNull
    static Interval map(TimespanWrapper timespanWrapper) {
        return new Interval(
                new Period(timespanWrapper.start(), timespanWrapper.end()),
                Optional.ofNullable(timespanWrapper.situation()).map(LabelWrapper::label).orElse(null),
                Optional.ofNullable(timespanWrapper.cause()).map(LabelWrapper::label).orElse(null),
                Optional.ofNullable(timespanWrapper.additionalTravelTime()).map(LabelWrapper::label).orElse(null),
                Optional.ofNullable(timespanWrapper.alternativeTransport()).map(LabelWrapper::label).orElse(null),
                new ArrayList<>(timespanWrapper.advices())
        );
    }

    @NonNull
    static DisturbanceTypeWrapper map(DisturbanceType disturbanceType) {
        return switch (disturbanceType) {
            case CALAMITY -> DisturbanceTypeWrapper.CALAMITY;
            case DISRUPTION -> DisturbanceTypeWrapper.DISRUPTION;
            case MAINTENANCE -> DisturbanceTypeWrapper.MAINTENANCE;
        };
    }
}
