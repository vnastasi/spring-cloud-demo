package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Disruption;
import md.vnastasi.cloud.exception.ApiException;
import md.vnastasi.cloud.service.DisruptionService;
import md.vnastasi.cloud.util.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static md.vnastasi.cloud.util.AssertionUtils.createRecursiveComparisonConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {DisruptionEndpoint.class, ApiErrorAttributes.class})
class DisruptionEndpointTest {

    @MockBean
    private DisruptionService mockDisruptionService;

    @Autowired
    private WebTestClient client;

    @Test
    void testEmptyListResponse() {
        when(mockDisruptionService.getDisruptions(anyList())).thenReturn(Flux.empty());

        client.get().uri("/disruption").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Disruption.class).hasSize(0);
    }

    @Test
    void testNonEmptyListResponse() {
        when(mockDisruptionService.getDisruptions(anyList())).thenReturn(Flux.just(TestData.OUT_DISRUPTION));

        client.get()
                .uri("/disruption")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Disruption.class).hasSize(1)
                .value(list -> {
                    assertThat(list).hasSize(1);
                    assertThat(list.get(0)).usingRecursiveComparison(createRecursiveComparisonConfiguration()).isEqualTo(TestData.OUT_DISRUPTION);
                });
    }

    @Test
    void testErrorResponse() {
        when(mockDisruptionService.getDisruptions(anyList())).thenReturn(Flux.error(new ApiException()));

        client.get()
                .uri("/disruption")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
