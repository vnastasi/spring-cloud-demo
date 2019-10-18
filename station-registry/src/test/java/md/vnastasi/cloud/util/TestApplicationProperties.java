package md.vnastasi.cloud.util;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import md.vnastasi.cloud.ApplicationProperties;

@Builder
@Value
public final class TestApplicationProperties {

    @Builder.Default
    private String nsApiBaseUrl = "https://stub";

    @Builder.Default
    private String nsApiPublicTravelInfoBasePath = "/pti";

    @Builder.Default
    private String nsApiStationsPath = "/stations";

    @Builder.Default
    private String nsApiKeyHeader = "NS-API-KEY";

    @Builder.Default
    private String nsApiKeyValue = "1234567890";

    @NonNull
    public ApplicationProperties convert() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        ApplicationProperties.NsApiProperties nsApiProperties = new ApplicationProperties.NsApiProperties();
        ApplicationProperties.NsApiProperties.PublicTravelInfoProperties publicTravelInfoProperties = new ApplicationProperties.NsApiProperties.PublicTravelInfoProperties();
        ApplicationProperties.NsApiProperties.PublicTravelInfoProperties.ApiKeyProperties apiKeyProperties = new ApplicationProperties.NsApiProperties.PublicTravelInfoProperties.ApiKeyProperties();

        apiKeyProperties.setHeader(nsApiKeyHeader);
        apiKeyProperties.setValue(nsApiKeyValue);
        publicTravelInfoProperties.setApiKey(apiKeyProperties);
        publicTravelInfoProperties.setBasePath(nsApiPublicTravelInfoBasePath);
        publicTravelInfoProperties.setStationsPath(nsApiStationsPath);
        nsApiProperties.setPublicTravelInfo(publicTravelInfoProperties);
        nsApiProperties.setBaseUrl(nsApiBaseUrl);
        applicationProperties.setNsApi(nsApiProperties);

        return applicationProperties;
    }
}
