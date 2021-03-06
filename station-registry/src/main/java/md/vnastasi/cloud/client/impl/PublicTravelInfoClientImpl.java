package md.vnastasi.cloud.client.impl;

import md.vnastasi.cloud.ApplicationProperties;
import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.client.model.StationWrapper;
import md.vnastasi.cloud.client.model.StationsResponseWrapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class PublicTravelInfoClientImpl implements PublicTravelInfoClient {

    private final WebClient webClient;
    private final ApplicationProperties applicationProperties;

    public PublicTravelInfoClientImpl(
            @NonNull WebClient webClient,
            @NonNull ApplicationProperties applicationProperties
    ) {
        this.webClient = webClient;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public Flux<StationWrapper> getStations() {
        var url = applicationProperties.nsApi().publicTravelInfo().stationsPath();
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(StationsResponseWrapper.class)
                .flatMapIterable(StationsResponseWrapper::payload);
    }
}
