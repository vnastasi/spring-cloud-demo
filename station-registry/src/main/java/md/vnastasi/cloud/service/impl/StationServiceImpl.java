package md.vnastasi.cloud.service.impl;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.service.StationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static java.util.Comparator.comparingDouble;

@Service
public class StationServiceImpl implements StationService {

    private final PublicTravelInfoClient client;
    private final DistanceCalculator distanceCalculator;

    public StationServiceImpl(PublicTravelInfoClient client, DistanceCalculator distanceCalculator) {
        this.client = client;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Flux<Station> getStations() {
        return client.getStations().map(Mappings::map);
    }

    @Override
    public Flux<Station> getNearbyStations(Coordinates coordinates, int limit) {
        return getStations()
                .map(it -> VectorStation.builder().station(it).distance(distanceCalculator.calculate(coordinates, it.getCoordinates())).build())
                .sort(comparingDouble(s -> s.distance))
                .map(it -> it.station)
                .take(limit);
    }

    @Value
    @Builder
    private static class VectorStation {

        @NonNull
        Station station;

        double distance;
    }
}
