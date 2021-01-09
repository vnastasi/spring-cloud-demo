package md.vnastasi.cloud.client.impl;

import md.vnastasi.cloud.ApplicationProperties;
import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.util.JsonUtils;
import md.vnastasi.cloud.util.TestApplicationProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PublicTravelInfoClientTest {

    private final ApplicationProperties applicationProperties = TestApplicationProperties.builder().build().convert();
    private final MockWebServer webServer = new MockWebServer();
    private final WebClient webClient = WebClient.create(webServer.url("/").toString());

    private final PublicTravelInfoClient client = new PublicTravelInfoClientImpl(webClient, applicationProperties);

    @AfterEach
    void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    @DisplayName("when client returns HTTP 200 then expect a flux of stations")
    void testStationsResponse() {
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(JsonUtils.readString("station_list.json"));
        webServer.enqueue(mockResponse);

        StepVerifier.withVirtualTime(client::getStations)
                .assertNext(it -> assertThat(it.names().longName()).isEqualTo("London St. Pancras Int."))
                .assertNext(it -> assertThat(it.names().longName()).isEqualTo("Breukelen"))
                .assertNext(it -> assertThat(it.names().longName()).isEqualTo("Breda"))
                .verifyComplete();
    }
}