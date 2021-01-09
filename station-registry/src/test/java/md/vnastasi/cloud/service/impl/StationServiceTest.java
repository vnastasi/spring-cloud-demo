package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.client.model.StationWrapper;
import md.vnastasi.cloud.endpoint.model.Coordinates;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StationServiceTest {

    private final PublicTravelInfoClient mockClient = mock(PublicTravelInfoClient.class);
    private final DistanceCalculator mockCalculator = mock(DistanceCalculator.class);

    private final StationService service = new StationServiceImpl(mockClient, mockCalculator);

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
        var stationWrapper = JsonUtils.deserialize("station_BKL.json", StationWrapper.class);
        when(mockClient.getStations()).thenReturn(Flux.just(stationWrapper));

        StepVerifier.withVirtualTime(service::getStations)
                .assertNext(this::assertStation)
                .verifyComplete();
    }

    @Test
    @DisplayName("when nearby stations requested then expect list order by least distance")
    void testNearbyStations() throws IOException {
        var coordinates = new Coordinates(1.0, 1.0);

        var station1 = JsonUtils.deserialize("station_BKL.json", StationWrapper.class);
        var station2 = JsonUtils.deserialize("station_LEDN.json", StationWrapper.class);
        var station3 = JsonUtils.deserialize("station_MTN.json", StationWrapper.class);
        when(mockClient.getStations()).thenReturn(Flux.just(station1, station2, station3));

        when(mockCalculator.calculate(eq(coordinates), any(Coordinates.class))).thenReturn(2.0, 1.0, 3.0);


        StepVerifier.withVirtualTime(() -> service.getNearbyStations(coordinates, 3))
                .assertNext(it -> assertThat(it.station().code()).isEqualTo("8400390"))
                .assertNext(it -> assertThat(it.station().code()).isEqualTo("8400133"))
                .assertNext(it -> assertThat(it.station().code()).isEqualTo("8400449"))
                .verifyComplete();
    }

    private void assertStation(Station station) {
        assertThat(station).isNotNull();
        assertThat(station.code()).isEqualTo("8400133");
        assertThat(station.type()).isEqualTo(StationType.STOP_TRAIN_JUNCTION_STATION);
        assertThat(station.tracks()).hasSize(2).containsExactlyInAnyOrder("2", "3");
        assertThat(station.synonyms()).isEmpty();

        var names = station.names();
        assertThat(names.shortName()).isEqualTo("Breukelen");
        assertThat(names.middleName()).isEqualTo("Breukelen");
        assertThat(names.longName()).isEqualTo("Breukelen");

        var coordinates = station.coordinates();
        assertThat(coordinates.latitude()).isCloseTo(52.17149, offset(0.001));
        assertThat(coordinates.longitude()).isCloseTo(4.9906, offset(0.001));
    }
}
