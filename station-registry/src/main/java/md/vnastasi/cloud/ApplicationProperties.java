package md.vnastasi.cloud;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConfigurationProperties(prefix = "application")
@ConstructorBinding
public class ApplicationProperties {

    NsApiProperties nsApi;

    @Value
    public static class NsApiProperties {

        String baseUrl;
        PublicTravelInfoProperties publicTravelInfo;

        @Value
        public static class PublicTravelInfoProperties {

            String basePath;
            ApiKeyProperties apiKey;
            String stationsPath;

            @Value
            public static class ApiKeyProperties {

                String header;
                String value;
            }
        }
    }
}
