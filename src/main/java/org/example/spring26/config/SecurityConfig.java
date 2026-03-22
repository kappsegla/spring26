package org.example.spring26.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.webauthn.management.JdbcPublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.JdbcUserCredentialRepository;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;


@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/", "/login", "/login/webauthn", "/signup", "/error").permitAll()
                        .requestMatchers("/webauthn/authenticate/**").permitAll()
                        .requestMatchers("/device-link").permitAll()

                        // Authenticated endpoints
                        .requestMatchers("/profile", "/logout").authenticated()
                        .requestMatchers("/device-link/create").authenticated()

                        // Elevated permission endpoints (Temporary or Full access)
                        .requestMatchers("/add-passkey").hasAnyRole("USER", "TEMP")
                        .requestMatchers("/webauthn/register/**").hasAnyRole("USER", "TEMP")

                        .anyRequest().authenticated()
                )
                .webAuthn(webAuthn -> webAuthn
                        .rpId("localhost")
                        .rpName("Spring Boot Passkey Demo")
                        .allowedOrigins("http://localhost:8080")
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .build();
    }

    @Bean
    PublicKeyCredentialUserEntityRepository jdbcPublicKeyCredentialRepository(JdbcOperations jdbc) {
        return new JdbcPublicKeyCredentialUserEntityRepository(jdbc);
    }

    @Bean
    UserCredentialRepository jdbcUserCredentialRepository(JdbcOperations jdbc) {
        return new JdbcUserCredentialRepository(jdbc);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.builder()
                .username(username)
                .password("{noop}") // No password needed - passkey only
                .roles("USER")
                .build();
    }

//    @Bean
//    public FilterRegistrationBean<DebugFilter> debugFilter() {
//        FilterRegistrationBean<DebugFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new DebugFilter());
//        registration.addUrlPatterns("/login/webauthn");
//        registration.setOrder(Integer.MIN_VALUE);
//        return registration;
//    }

}
