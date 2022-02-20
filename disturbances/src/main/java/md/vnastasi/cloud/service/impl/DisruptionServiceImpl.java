package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.client.model.DisruptionWrapper;
import md.vnastasi.cloud.endpoint.model.Disruption;
import md.vnastasi.cloud.endpoint.model.DisturbanceType;
import md.vnastasi.cloud.service.DisruptionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisruptionServiceImpl implements DisruptionService {

    private final PublicTravelInfoClient client;

    public DisruptionServiceImpl(PublicTravelInfoClient client) {
        this.client = client;
    }

    @Override
    public Flux<Disruption> getDisruptions(List<DisturbanceType> types) {
        return client.getDisturbances(types.stream().map(Mappings::map).collect(Collectors.toList()))
                .filter(it -> it instanceof DisruptionWrapper)
                .map(it -> Mappings.map((DisruptionWrapper) it));
    }
}
