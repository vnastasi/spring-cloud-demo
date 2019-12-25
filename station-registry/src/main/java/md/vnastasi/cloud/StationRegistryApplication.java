package md.vnastasi.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StationRegistryApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(StationRegistryApplication.class).build();
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
