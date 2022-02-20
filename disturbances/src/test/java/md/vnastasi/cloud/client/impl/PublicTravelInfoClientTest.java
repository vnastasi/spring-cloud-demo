package md.vnastasi.cloud.client.impl;

import md.vnastasi.cloud.ApplicationProperties;
import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.exception.ApiException;
import md.vnastasi.cloud.util.JsonUtils;
import md.vnastasi.cloud.util.TestApplicationProperties;
import md.vnastasi.cloud.util.TestData;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static md.vnastasi.cloud.util.AssertionUtils.createRecursiveComparisonConfiguration;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PublicTravelInfoClientTest {

    private final ApplicationProperties applicationProperties = new TestApplicationProperties().convert();
    private final MockWebServer webServer = new MockWebServer();
    private final WebClient webClient = WebClient.create(webServer.url("/").toString());

    private final PublicTravelInfoClient client = new PublicTravelInfoClientImpl(webClient, applicationProperties);

    @AfterEach
    void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    @DisplayName("when client receives HTTP 500 then expect ApiException")
    void testErrorResponse() {
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(JsonUtils.readString("error.json"));
        webServer.enqueue(mockResponse);

        StepVerifier.withVirtualTime(() -> client.getDisturbances(List.of()))
                .expectErrorMatches(it -> it instanceof ApiException)
                .verify();
    }

    @Test
    @DisplayName("when client receives HTTP 200 and empty list then expect empty Flux")
    void testEmptyList() {
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(JsonUtils.readString("empty_list.json"));
        webServer.enqueue(mockResponse);

        StepVerifier.withVirtualTime(() -> client.getDisturbances(List.of()))
                .verifyComplete();
    }

    @Test
    @DisplayName("when client receives HTTP 200 and malformed response then expect DecodingException")
    void testMalformedResponse() {
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(JsonUtils.readString("malformed.json"));
        webServer.enqueue(mockResponse);

        StepVerifier.withVirtualTime(() -> client.getDisturbances(List.of()))
                .expectErrorMatches(it -> it instanceof DecodingException)
                .verify();
    }

    @Test
    @DisplayName("when client receives HTTP 200 and valid list response then expect correct list of records")
    void testDisturbanceList() {
        var mockResponse = new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(JsonUtils.readString("disruption_list.json"));
        webServer.enqueue(mockResponse);

        StepVerifier.withVirtualTime(() -> client.getDisturbances(List.of()))
                .assertNext(it -> assertThat(it).usingRecursiveComparison(createRecursiveComparisonConfiguration()).isEqualTo(TestData.DISRUPTION))
                .assertNext(it -> assertThat(it).usingRecursiveComparison(createRecursiveComparisonConfiguration()).isEqualTo(TestData.CALAMITY))
                .assertNext(it -> assertThat(it).usingRecursiveComparison(createRecursiveComparisonConfiguration()).isEqualTo(TestData.MAINTENANCE))
                .verifyComplete();
    }
}
