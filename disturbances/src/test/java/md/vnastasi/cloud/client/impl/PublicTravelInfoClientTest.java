package md.vnastasi.cloud.client.impl;

import md.vnastasi.cloud.ApplicationProperties;
import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.client.model.*;
import md.vnastasi.cloud.exception.ApiException;
import md.vnastasi.cloud.util.JsonUtils;
import md.vnastasi.cloud.util.OffsetDateTimeComparator;
import md.vnastasi.cloud.util.TestApplicationProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        var recursiveComparisonConfiguration = createRecursiveComparisonConfiguration();

        StepVerifier.withVirtualTime(() -> client.getDisturbances(List.of()))
                .assertNext(value -> {
                    var expectedValue = new DisruptionWrapper(
                            "6014080",
                            DisturbanceTypeWrapper.DISRUPTION,
                            "Amsterdam-Rotterdam-Brussel (HSL)",
                            true,
                            asOffsetDatetime("2021-12-28T10:31:00+0100"),
                            asOffsetDatetime("2022-01-09T22:00:00+0100"),
                            List.of(
                                    new TimespanWrapper(
                                            asOffsetDatetime("2021-12-28T10:31:00+0100"),
                                            asOffsetDatetime("2022-01-09T22:00:00+0100"),
                                            new LabelWrapper("Tussen Amsterdam Centraal en Brussel-Zuid Midi is voldoende afstand houden in de trein onmogelijk door grote drukte."),
                                            new LabelWrapper("Grote drukte"),
                                            null,
                                            null,
                                            List.of(
                                                    "We raden af, om met de trein naar België te reizen, tenzij het echt noodzakelijk is.."
                                            )
                                    )
                            )
                    );
                    assertThat(value).usingRecursiveComparison(recursiveComparisonConfiguration).isEqualTo(expectedValue);
                })
                .assertNext(value -> {
                    var expectedValue = new CalamityWrapper(
                            "df93b6d7-1fba-462d-8578-cf4b90ed7870",
                            DisturbanceTypeWrapper.CALAMITY,
                            "Tijdelijk minder treinen",
                            "De komende periode rijden we tijdelijk minder treinen in de spits. Ook valt  incidenteel een nachttrein in het weekend uit. Het reguliere nachtnet, inclusief de verbinding naar Schiphol, blijft ongewijzigd. Plan je reis vooraf in de reisplanner, deze wordt per dag bijgewerkt.",
                            asOffsetDatetime("2021-12-27T22:34:00+0100"),
                            "https://www.ns.nl/reisinformatie/calamiteiten/tijdelijk-minder-treinen-in-de-spits.html",
                            true
                    );
                    assertThat(value).usingRecursiveComparison(recursiveComparisonConfiguration).isEqualTo(expectedValue);
                })
                .assertNext(value -> {
                    var expectedValue = new DisruptionWrapper(
                            "7001734",
                            DisturbanceTypeWrapper.MAINTENANCE,
                            "Eindhoven - Sittard",
                            false,
                            asOffsetDatetime("2022-01-01T22:20:00+0100"),
                            asOffsetDatetime("2022-01-07T02:00:00+0100"),
                            List.of(
                                    new TimespanWrapper(
                                            asOffsetDatetime("2022-01-01T22:20:00+0100"),
                                            asOffsetDatetime("2022-01-02T02:00:00+0100"),
                                            new LabelWrapper("Door werkzaamheden: tussen Weert en Sittard rijden er bussen."),
                                            new LabelWrapper("Werkzaamheden"),
                                            new LabelWrapper("De extra reistijd kan oplopen tot 30 minuten."),
                                            new LabelWrapper("Er rijden snelbussen."),
                                            List.of()
                                    ),
                                    new TimespanWrapper(
                                            asOffsetDatetime("2022-01-02T22:20:00+0100"),
                                            asOffsetDatetime("2022-01-03T02:00:00+0100"),
                                            new LabelWrapper("Door werkzaamheden: tussen Weert en Sittard rijden er bussen."),
                                            new LabelWrapper("Werkzaamheden"),
                                            new LabelWrapper("De extra reistijd kan oplopen tot 30 minuten."),
                                            new LabelWrapper("Er rijden snelbussen."),
                                            List.of()
                                    )
                            )
                    );
                    assertThat(value).usingRecursiveComparison(recursiveComparisonConfiguration).isEqualTo(expectedValue);
                })
                .verifyComplete();
    }

    private OffsetDateTime asOffsetDatetime(@NonNull String input) {
        return OffsetDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));
    }

    private RecursiveComparisonConfiguration createRecursiveComparisonConfiguration() {
        var config = new RecursiveComparisonConfiguration();
        config.ignoreAllOverriddenEquals();
        config.strictTypeChecking(true);
        config.registerComparatorForType(new OffsetDateTimeComparator(), OffsetDateTime.class);
        return config;
    }
}