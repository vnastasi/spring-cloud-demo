package md.vnastasi.cloud.util;

import md.vnastasi.cloud.ApplicationProperties;

public record TestApplicationProperties(
    String nsApiBaseUrl,
    String nsApiPublicTravelInfoBasePath,
    String nsApiDisruptionsPath,
    String nsApiKeyHeader,
    String nsApiKeyValue
) {

    public TestApplicationProperties() {
        this("https://stub", "/pti", "/disruptions", "NS-API-KEY", "1234567890");
    }

    public ApplicationProperties convert() {
        var apiKeyProperties = new ApplicationProperties.NsApiProperties.PublicTravelInfoProperties.ApiKeyProperties(nsApiKeyHeader, nsApiKeyValue);
        var publicTravelInfoProperties = new ApplicationProperties.NsApiProperties.PublicTravelInfoProperties(nsApiPublicTravelInfoBasePath, apiKeyProperties, nsApiDisruptionsPath);
        var nsApiProperties = new ApplicationProperties.NsApiProperties(nsApiBaseUrl, publicTravelInfoProperties);
        return new ApplicationProperties(nsApiProperties);
    }
}
