package md.vnastasi.cloud;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private NsApiProperties nsApi;

    @Data
    public static class NsApiProperties {

        private String baseUrl;
        private PublicTravelInfoProperties publicTravelInfo;

        @Data
        public static class PublicTravelInfoProperties {

            private String basePath;
            private ApiKeyProperties apiKey;
            private String stationsPath;

            @Data
            public static class ApiKeyProperties {

                private String header;
                private String value;
            }
        }
    }
}
