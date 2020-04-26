package md.vnastasi.cloud;

import lombok.NonNull;
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

        @NonNull String baseUrl;
        @NonNull PublicTravelInfoProperties publicTravelInfo;

        @Value
        public static class PublicTravelInfoProperties {

            @NonNull String basePath;
            @NonNull ApiKeyProperties apiKey;
            @NonNull String stationsPath;

            @Value
            public static class ApiKeyProperties {

                @NonNull String header;
                @NonNull String value;
            }
        }
    }
}
