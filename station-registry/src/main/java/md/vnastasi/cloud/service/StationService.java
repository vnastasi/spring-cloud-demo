package md.vnastasi.cloud.service;

import md.vnastasi.cloud.endpoint.model.Station;
import reactor.core.publisher.Flux;

public interface StationService {

    Flux<Station> getStations();
}
