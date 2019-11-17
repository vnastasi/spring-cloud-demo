package md.vnastasi.cloud;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private Security security;

    @Data
    public static class Security {

        private String user;
        private String password;
        private List<String> roles;
    }
}
