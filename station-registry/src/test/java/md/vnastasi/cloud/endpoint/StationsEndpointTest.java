package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.*;
import md.vnastasi.cloud.service.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = StationsEndpoint.class)
@TestPropertySource(
        properties = {
                "spring.cloud.config.enabled=false",
                "spring.config.location=classpath:test-application.yml"
        }
)
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
        var station = new DistanceAwareStation(createStation(), 1.0);
        var coordinates = new Coordinates(8.0, -19.7);
        when(mockService.getNearbyStations(eq(coordinates), eq(1))).thenReturn(Flux.just(station));


        Function<UriBuilder, URI> uriFunction = uriBuilder -> uriBuilder.path("/nearby")
                .queryParam("latitude", coordinates.latitude())
                .queryParam("longitude", coordinates.longitude())
                .queryParam("limit", 1)
                .build();

        client.get().uri(uriFunction).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DistanceAwareStation.class).hasSize(1)
                .contains(station);
    }

    private Station createStation() {
        var names = new NameHolder("Amsterdam C.", "Amsterdam C.", "Amsterdam Centraal");
        var coordinates = new Coordinates(4.6, 3.4);
        return new Station(
                "AMS",
                names,
                StationType.MAJOR_STATION,
                List.of(),
                "NL",
                List.of("1", "2", "3"),
                coordinates
        );
    }
}
