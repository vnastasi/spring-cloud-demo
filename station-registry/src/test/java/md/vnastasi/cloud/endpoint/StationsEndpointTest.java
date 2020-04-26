package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.*;
import md.vnastasi.cloud.service.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = StationsEndpoint.class)
class StationsEndpointTest {

    @MockBean
    private StationService mockService;

    @Autowired
    private WebTestClient client;

    @Test
    @DisplayName("when empty list then expect HTTP 200 and empty list in response body")
    void testEmptyListResponse() {
        when(mockService.getStations()).thenReturn(Flux.empty());

        client.get().uri("/").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Station.class).hasSize(0);
    }

    @Test
    @DisplayName("when list with one element then expect HTTP 200 and list with one element")
    void testNonEmptyListResponse() {
        var station = createStation();
        when(mockService.getStations()).thenReturn(Flux.just(station));

        client.get().uri("/").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Station.class).hasSize(1)
                .contains(station);
    }

    @Test
    @DisplayName("when nearby list with one element then expect HTTP 200 and list wih one element")
    void testNearbyStationList() {
        var station = DistanceAwareStation.builder().station(createStation()).distance(1.0).build();
        var coordinates = Coordinates.builder().latitude(8.0).longitude(-19.7).build();
        when(mockService.getNearbyStations(eq(coordinates), eq(1))).thenReturn(Flux.just(station));


        Function<UriBuilder, URI> uriFunction = uriBuilder -> uriBuilder.path("/nearby")
                .queryParam("latitude", coordinates.getLatitude())
                .queryParam("longitude", coordinates.getLongitude())
                .queryParam("limit", 1)
                .build();

        client.get().uri(uriFunction).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DistanceAwareStation.class).hasSize(1)
                .contains(station);
    }

    private Station createStation() {
        var names = NameHolder.builder()
                .shortName("Amsterdam C.")
                .middleName("Amsterdam C.")
                .longName("Amsterdam Centraal")
                .build();

        var coordinates = Coordinates.builder()
                .longitude(4.6)
                .latitude(3.4)
                .build();

        return Station.builder()
                .code("AMS")
                .countryCode("NL")
                .names(names)
                .coordinates(coordinates)
                .tracks(List.of("1", "2", "3"))
                .synonyms(List.of())
                .type(StationType.MAJOR_STATION)
                .build();
    }
}
