package org.example.spring26.config;

import org.example.spring26.filters.ApiKeyAuthenticationProvider;
import org.example.spring26.filters.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final ApiKeyAuthenticationProvider apiKeyProvider;

    public SecurityConfig(ApiKeyAuthenticationProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authManager,
                                                   UserDetailsService service) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/jtehome").permitAll()
                                .anyRequest().denyAll())
                .formLogin(Customizer.withDefaults())
                .userDetailsService(service)  //Needs to specify a UserDetailsService to get DaoAuthenticationProvider
                .authenticationProvider(apiKeyProvider)
                .addFilterBefore(new ApiKeyFilter(authManager),
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
