package md.vnastasi.cloud.service;

import md.vnastasi.cloud.endpoint.model.Station;
import reactor.core.publisher.Mono;

import java.util.List;

public interface StationService {

    Mono<List<Station>> getStations();
}
