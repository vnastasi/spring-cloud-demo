package md.vnastasi.cloud.service;

import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.DistanceAwareStation;
import md.vnastasi.cloud.endpoint.model.Station;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

public interface StationService {

    Flux<Station> getStations();

    Flux<DistanceAwareStation> getNearbyStations(@NonNull Coordinates coordinates, int limit);
}
