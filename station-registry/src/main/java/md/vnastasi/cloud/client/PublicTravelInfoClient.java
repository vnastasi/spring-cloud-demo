package md.vnastasi.cloud.client;

import md.vnastasi.cloud.client.model.StationWrapper;
import reactor.core.publisher.Flux;

public interface PublicTravelInfoClient {

    Flux<StationWrapper> getStations();
}
