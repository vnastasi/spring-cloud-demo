package md.vnastasi.cloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    @Bean
    public WebClient provideWebClient(
        @NonNull WebClient.Builder builder,
        @NonNull ApplicationProperties applicationProperties
    ) {
        var url = applicationProperties.nsApi().baseUrl() + applicationProperties.nsApi().publicTravelInfo().basePath();
        var headerName = applicationProperties.nsApi().publicTravelInfo().apiKey().header();
        var headerValue = applicationProperties.nsApi().publicTravelInfo().apiKey().value();

        return builder
            .baseUrl(url)
            .defaultHeader(headerName, headerValue)
            .build();
    }
}
