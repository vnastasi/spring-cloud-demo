package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.service.NotificationService;
import md.vnastasi.cloud.util.TestData;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static md.vnastasi.cloud.util.AssertionUtils.createRecursiveComparisonConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotificationServiceTest {

    private final PublicTravelInfoClient mockClient = mock(PublicTravelInfoClient.class);

    private final NotificationService service = new NotificationServiceImpl(mockClient);

    @Test
    void testGetNotifications() {
        when(mockClient.getDisturbances(anyList())).thenReturn(Flux.just(TestData.CALAMITY, TestData.DISRUPTION));

        StepVerifier.withVirtualTime(service::getNotifications)
                .assertNext(it -> assertThat(it).usingRecursiveComparison(createRecursiveComparisonConfiguration()).isEqualTo(TestData.OUT_NOTIFICATION))
                .verifyComplete();
    }
}
