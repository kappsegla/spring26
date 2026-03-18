package org.example.spring26.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/").denyAll()
                                .anyRequest().denyAll())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
