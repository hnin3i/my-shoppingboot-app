package ai.shoppingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF protection for now so simple forms post without tokens
            .csrf(csrf -> csrf.disable())

            // 2. Permit all HTTP requests (your Interceptors in WebConfig will protect routes)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // 3. Disable Spring Security's default login form and basic auth popups
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}