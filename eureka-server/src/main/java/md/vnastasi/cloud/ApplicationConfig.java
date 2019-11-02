package md.vnastasi.cloud;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    @ConditionalOnMissingBean
    public HttpTraceRepository provideHttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
