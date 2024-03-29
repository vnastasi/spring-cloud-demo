package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.DistanceAwareStation;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.service.StationService;
import org.springframework.lang.NonNull;
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
                    double distance = distanceCalculator.calculate(coordinates, station.coordinates());
                    return new DistanceAwareStation(station, distance);
                })
                .sort(comparingDouble(DistanceAwareStation::distance))
                .take(limit);
    }

    private synchronized Flux<Station> cachedFlux() {
        if (flux == null) {
            flux = client.getStations().map(Mappings::map).cache(Duration.ofHours(1));
        }
        return flux;
    }
}
