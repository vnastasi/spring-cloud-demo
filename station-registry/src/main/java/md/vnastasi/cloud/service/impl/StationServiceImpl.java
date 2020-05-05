package md.vnastasi.cloud.service.impl;

import lombok.NonNull;
import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.DistanceAwareStation;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.service.StationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static java.util.Comparator.comparingDouble;

@Service
public class StationServiceImpl implements StationService {

    private final PublicTravelInfoClient client;
    private final DistanceCalculator distanceCalculator;

    private Flux<Station> flux;

    public StationServiceImpl(
            @NonNull PublicTravelInfoClient client,
            @NonNull DistanceCalculator distanceCalculator
    ) {
        this.client = client;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Flux<Station> getStations() {
        return cachedFlux();
    }

    @Override
    public Flux<DistanceAwareStation> getNearbyStations(@NonNull Coordinates coordinates, int limit) {
        return getStations()
                .map(station -> {
                    double distance = distanceCalculator.calculate(coordinates, station.getCoordinates());
                    return DistanceAwareStation.builder().station(station).distance(distance).build();
                })
                .sort(comparingDouble(DistanceAwareStation::getDistance))
                .take(limit);
    }

    private Flux<Station> cachedFlux() {
        if (flux == null) {
            flux = client.getStations().map(Mappings::map).cache(Duration.ofHours(1));
        }
        return flux;
    }
}
