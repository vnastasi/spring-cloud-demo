package md.vnastasi.cloud.client;

import md.vnastasi.cloud.client.model.DisturbanceTypeWrapper;
import md.vnastasi.cloud.client.model.DisturbanceWrapper;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PublicTravelInfoClient {

    Flux<DisturbanceWrapper> getDisturbances(List<DisturbanceTypeWrapper> disturbanceTypes);
}
