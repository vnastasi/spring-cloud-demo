package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.NameHolder;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.endpoint.model.StationType;
import md.vnastasi.cloud.service.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

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
        when(mockService.getStations()).thenReturn(Mono.just(List.of()));

        client.get().uri("/").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Station.class).hasSize(0);
    }

    @Test
    @DisplayName("when list with one element then expect HTTP 200 and list with one element")
    void testNonEmptyListResponse() {
        Station station = createStation();
        when(mockService.getStations()).thenReturn(Mono.just(List.of(station)));

        client.get().uri("/").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Station.class).hasSize(1)
                .contains(station);

    }

    private Station createStation() {
        NameHolder names = NameHolder.builder()
                .shortName("Amsterdam C.")
                .middleName("Amsterdam C.")
                .longName("Amsterdam Centraal")
                .build();
        Coordinates coordinates = Coordinates.builder()
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
