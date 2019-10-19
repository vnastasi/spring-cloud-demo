package md.vnastasi.cloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    @Bean
    public WebClient provideWebClient(
            WebClient.Builder builder,
            ApplicationProperties applicationProperties
    ) {
        String url = applicationProperties.getNsApi().getBaseUrl() + applicationProperties.getNsApi().getPublicTravelInfo().getBasePath();
        String headerName = applicationProperties.getNsApi().getPublicTravelInfo().getApiKey().getHeader();
        String headerValue = applicationProperties.getNsApi().getPublicTravelInfo().getApiKey().getValue();

        return builder.baseUrl(url).defaultHeader(headerName, headerValue).build();
    }
}
