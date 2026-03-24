package org.example.spring26.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


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
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
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
