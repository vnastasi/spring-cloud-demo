package md.vnastasi.cloud;

import io.netty.bootstrap.Bootstrap;
import md.vnastasi.cloud.log.HttpClientLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.lang.NonNull;
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
        final var url = applicationProperties.nsApi().baseUrl() + applicationProperties.nsApi().publicTravelInfo().basePath();
        final var headerName = applicationProperties.nsApi().publicTravelInfo().apiKey().header();
        final var headerValue = applicationProperties.nsApi().publicTravelInfo().apiKey().value();

        return builder
            .clientConnector(new ReactorClientHttpConnector(createHttpClient()))
            .baseUrl(url)
            .defaultHeader(headerName, headerValue)
            .build();
    }

    @NonNull
    private HttpClient createHttpClient() {
        final Function<Bootstrap, Bootstrap> bootstrapFunction = bootstrap -> BootstrapHandlers.updateLogSupport(bootstrap, new HttpClientLogger(HttpClient.class));
        final Function<TcpClient, TcpClient> tcpClientFunction = tcpClient -> tcpClient.bootstrap(bootstrapFunction);
        return HttpClient.create().tcpConfiguration(tcpClientFunction);
    }
}
