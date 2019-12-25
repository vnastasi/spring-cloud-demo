package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.client.model.StationWrapper;
import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.NameHolder;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.endpoint.model.StationType;
import md.vnastasi.cloud.service.StationService;
import md.vnastasi.cloud.util.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StationServiceTest {

    private PublicTravelInfoClient mockClient = mock(PublicTravelInfoClient.class);

    private StationService service = new StationServiceImpl(mockClient);

    @Test
    @DisplayName("when client returns empty flux then expect empty list")
    void testEmptyFlux() {
        when(mockClient.getStations()).thenReturn(Flux.empty());

        StepVerifier.withVirtualTime(service::getStations)
                .verifyComplete();
    }

    @Test
    @DisplayName("when client returns flux of one element then expect a list with that element")
    void testNonEmptyFlux() throws IOException {
        StationWrapper stationWrapper = JsonUtils.deserialize("station_BKL.json", StationWrapper.class);
        when(mockClient.getStations()).thenReturn(Flux.just(stationWrapper));

        StepVerifier.withVirtualTime(service::getStations)
                .assertNext(this::assertStation)
                .verifyComplete();
    }

    private void assertStation(Station station) {
        assertThat(station).isNotNull();
        assertThat(station.getCode()).isEqualTo("8400133");
        assertThat(station.getType()).isEqualTo(StationType.STOP_TRAIN_JUNCTION_STATION);
        assertThat(station.getTracks()).hasSize(2).containsExactlyInAnyOrder("2", "3");
        assertThat(station.getSynonyms()).isEmpty();

        NameHolder names = station.getNames();
        assertThat(names.getShortName()).isEqualTo("Breukelen");
        assertThat(names.getMiddleName()).isEqualTo("Breukelen");
        assertThat(names.getLongName()).isEqualTo("Breukelen");

        Coordinates coordinates = station.getCoordinates();
        assertThat(coordinates.getLatitude()).isCloseTo(52.17149, offset(0.001));
        assertThat(coordinates.getLongitude()).isCloseTo(4.9906, offset(0.001));
    }
}
