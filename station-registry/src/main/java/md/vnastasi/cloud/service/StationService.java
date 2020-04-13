package md.vnastasi.cloud.service;

import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.Station;
import reactor.core.publisher.Flux;

public interface StationService {

    Flux<Station> getStations();

    Flux<Station> getNearbyStations(Coordinates coordinates, int limit);
}
