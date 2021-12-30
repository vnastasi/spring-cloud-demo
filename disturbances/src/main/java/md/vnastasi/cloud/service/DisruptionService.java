package md.vnastasi.cloud.service;

import md.vnastasi.cloud.endpoint.model.Disruption;
import md.vnastasi.cloud.endpoint.model.DisturbanceType;
import reactor.core.publisher.Flux;

import java.util.List;

public interface DisruptionService {

    Flux<Disruption> getDisruptions(List<DisturbanceType> types);
}
