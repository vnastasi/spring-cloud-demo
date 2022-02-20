package md.vnastasi.cloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "application")
@ConstructorBinding
public record ApplicationProperties(
    NsApiProperties nsApi
) {

    public record NsApiProperties(
        String baseUrl,
        PublicTravelInfoProperties publicTravelInfo
    ) {

        public record PublicTravelInfoProperties(
            String basePath,
            ApiKeyProperties apiKey,
            String stationsPath
        ) {

            public record ApiKeyProperties(
                String header,
                String value
            ) {
            }
        }
    }
}
