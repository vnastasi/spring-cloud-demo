package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.service.DisruptionService;
import md.vnastasi.cloud.util.TestData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static md.vnastasi.cloud.util.AssertionUtils.createRecursiveComparisonConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DisruptionServiceTest {

    private final PublicTravelInfoClient mockClient = mock(PublicTravelInfoClient.class);

    private final DisruptionService service = new DisruptionServiceImpl(mockClient);

    @Test
    void testGetDisruptions() {
        when(mockClient.getDisturbances(any())).thenReturn(Flux.just(TestData.CALAMITY, TestData.DISRUPTION));

        StepVerifier.withVirtualTime(() -> service.getDisruptions(List.of()))
                .assertNext(it -> assertThat(it).usingRecursiveComparison(createRecursiveComparisonConfiguration()).isEqualTo(TestData.OUT_DISRUPTION))
                .verifyComplete();
    }
}
