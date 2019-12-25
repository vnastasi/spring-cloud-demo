package md.vnastasi.cloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService provideUserDetailsService(ApplicationProperties applicationProperties) {
        String user = applicationProperties.getSecurity().getUser();
        String password = applicationProperties.getSecurity().getPassword();
        String[] roles = applicationProperties.getSecurity().getRoles().toArray(new String[] {});
        UserDetails userDetails = User.builder()
                .username(user)
                .password("{noop}" + password)
                .roles(roles)
                .build();
        return new MapReactiveUserDetailsService(userDetails);
    }

    @Bean
    public SecurityWebFilterChain provideSecurityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers("/api/**").hasAnyRole("API-USER")
                .and()
                .build();
    }
}
