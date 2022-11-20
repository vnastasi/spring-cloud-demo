package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Notification;
import md.vnastasi.cloud.service.NotificationService;
import md.vnastasi.cloud.util.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static md.vnastasi.cloud.util.AssertionUtils.createRecursiveComparisonConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {NotificationEndpoint.class, ApiErrorAttributes.class})
@TestPropertySource(
        properties = {
                "spring.cloud.config.enabled=false",
                "spring.config.location=classpath:test-application.yml"
        }
)
class NotificationEndpointTest {

    @MockBean
    private NotificationService mockNotificationService;

    @Autowired
    private WebTestClient client;

    @Test
    void testEmptyListResponse() {
        when(mockNotificationService.getNotifications()).thenReturn(Flux.empty());

        client.get()
                .uri("/notification")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Notification.class)
                .hasSize(0);
    }

    @Test
    void testNonEmptyListResponse() {
        when(mockNotificationService.getNotifications()).thenReturn(Flux.just(TestData.OUT_NOTIFICATION));

        client.get()
                .uri("/notification")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Notification.class)
                .value(list -> {
                    assertThat(list).hasSize(1);
                    assertThat(list.get(0)).usingRecursiveComparison(createRecursiveComparisonConfiguration()).isEqualTo(TestData.OUT_NOTIFICATION);
                });
    }

    @Test
    void testErrorResponse() {
        when(mockNotificationService.getNotifications()).thenReturn(Flux.error(new DecodingException("Error parsing JSON")));

        client.get()
                .uri("/notification")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
