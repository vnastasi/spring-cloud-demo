package md.vnastasi.cloud.client.impl;

import md.vnastasi.cloud.ApplicationProperties;
import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.client.model.DisturbanceTypeWrapper;
import md.vnastasi.cloud.client.model.DisturbanceWrapper;
import md.vnastasi.cloud.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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
    public Flux<DisturbanceWrapper> getDisturbances(List<DisturbanceTypeWrapper> disturbanceTypes) {
        var url = applicationProperties.nsApi().publicTravelInfo().disruptionsPath();
        var allowedTypes = disturbanceTypes.stream().map(DisturbanceTypeWrapper::name).collect(Collectors.joining(","));
        return webClient.get()
                .uri(builder -> {
                    builder.path(url);
                    if (!allowedTypes.isEmpty()) {
                        builder.queryParam("type", allowedTypes);
                    }
                    return builder.build();
                })
                .retrieve()
                .onStatus(
                        status -> status != HttpStatus.OK,
                        response -> Mono.error(new ApiException())
                )
                .bodyToFlux(DisturbanceWrapper.class);
    }
}
