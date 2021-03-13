package md.vnastasi.cloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties(prefix = "application")
@ConstructorBinding
public record ApplicationProperties(
        Security security
) {

    public static record Security(
            String user,
            String password,
            List<String> roles
    ) {}
}
