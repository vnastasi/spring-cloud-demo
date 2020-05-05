package md.vnastasi.cloud;

import io.netty.bootstrap.Bootstrap;
import lombok.NonNull;
import md.vnastasi.cloud.log.HttpClientLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.channel.BootstrapHandlers;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.function.Function;

@Configuration
public class ApplicationConfig {

    @Bean
    public WebClient provideWebClient(
            @NonNull WebClient.Builder builder,
            @NonNull ApplicationProperties applicationProperties
    ) {
        var url = applicationProperties.getNsApi().getBaseUrl() + applicationProperties.getNsApi().getPublicTravelInfo().getBasePath();
        var headerName = applicationProperties.getNsApi().getPublicTravelInfo().getApiKey().getHeader();
        var headerValue = applicationProperties.getNsApi().getPublicTravelInfo().getApiKey().getValue();

        return builder
                .clientConnector(new ReactorClientHttpConnector(createHttpClient()))
                .baseUrl(url)
                .defaultHeader(headerName, headerValue)
                .build();
    }

    private HttpClient createHttpClient() {
        Function<Bootstrap, Bootstrap> bootstrapFunction = bootstrap -> BootstrapHandlers.updateLogSupport(bootstrap, new HttpClientLogger(HttpClient.class));
        Function<TcpClient, TcpClient> tcpClientFunction = tcpClient -> tcpClient.bootstrap(bootstrapFunction);
        return HttpClient.create().tcpConfiguration(tcpClientFunction);
    }
}
