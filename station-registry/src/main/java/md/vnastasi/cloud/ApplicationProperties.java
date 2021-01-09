package md.vnastasi.cloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "application")
@ConstructorBinding
public record ApplicationProperties(
    NsApiProperties nsApi
) {

    public static record NsApiProperties(
        String baseUrl,
        PublicTravelInfoProperties publicTravelInfo
    ) {

        public static record PublicTravelInfoProperties(
            String basePath,
            ApiKeyProperties apiKey,
            String stationsPath
        ) {

            public static record ApiKeyProperties(
                String header,
                String value
            ) {
            }
        }
    }
}
