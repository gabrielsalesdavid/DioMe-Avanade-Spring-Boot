package br.com.dioavanade.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .requestMatchers("/api/Books/**").authenticated()
                .requestMatchers("/oauth2-redirect.html", "/swagger-uri.html", "/v3/api-docs", "/swagger-uri/index.html", "/swagger-uri/**",
                        "/api/loggedin/confirm/**", "/api/loggiedin/confirm/", "/public/oauth2-redirect.html", "/context-path/v3/api-docs",
                        "/context-path/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
                        "/context-path/v3/api-docs/**", "/swagger-uri-custom.hmtl").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(server -> server.jwt());

        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs/**");
    }
}
