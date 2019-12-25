package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.service.StationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StationServiceImpl implements StationService {

    private final PublicTravelInfoClient client;

    public StationServiceImpl(PublicTravelInfoClient client) {
        this.client = client;
    }

    @Override
    public Flux<Station> getStations() {
        return client.getStations().map(Mappings::map);
    }
}
