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
        var url = applicationProperties.getNsApi().getBaseUrl() + applicationProperties.getNsApi().getPublicTravelInfo().getBasePath();
        var headerName = applicationProperties.getNsApi().getPublicTravelInfo().getApiKey().getHeader();
        var headerValue = applicationProperties.getNsApi().getPublicTravelInfo().getApiKey().getValue();

        return builder.baseUrl(url).defaultHeader(headerName, headerValue).build();
    }
}
