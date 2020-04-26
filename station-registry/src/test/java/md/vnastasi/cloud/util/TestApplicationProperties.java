package md.vnastasi.cloud.util;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import md.vnastasi.cloud.ApplicationProperties;

@Builder
@Value
public class TestApplicationProperties {

    @Builder.Default String nsApiBaseUrl = "https://stub";

    @Builder.Default String nsApiPublicTravelInfoBasePath = "/pti";

    @Builder.Default String nsApiStationsPath = "/stations";

    @Builder.Default String nsApiKeyHeader = "NS-API-KEY";

    @Builder.Default String nsApiKeyValue = "1234567890";

    @NonNull
    public ApplicationProperties convert() {
        var apiKeyProperties = new ApplicationProperties.NsApiProperties.PublicTravelInfoProperties.ApiKeyProperties(nsApiKeyHeader, nsApiKeyValue);
        var publicTravelInfoProperties = new ApplicationProperties.NsApiProperties.PublicTravelInfoProperties(nsApiPublicTravelInfoBasePath, apiKeyProperties, nsApiStationsPath);
        var nsApiProperties = new ApplicationProperties.NsApiProperties(nsApiBaseUrl, publicTravelInfoProperties);
        return new ApplicationProperties(nsApiProperties);
    }
}
