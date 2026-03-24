package org.example.spring26.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.oidcUserService())
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .build();
    }


    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            // 1. Låt Spring hämta ID Token + UserInfo
            OidcUser oidcUser = delegate.loadUser(userRequest);

            // 2. Extrahera claims
            String email = oidcUser.getEmail();
            String name = oidcUser.getFullName();
            String picture = (String) oidcUser.getClaims().get("picture");
            String sub = oidcUser.getSubject(); // stabilt unikt ID från Google

            // 3. Spara eller uppdatera i din databas
            //userRepository.upsertFromGoogle(sub, email, name, picture);
            log.info("User {} has been successfully logged in", email);

            // 4. Returnera användaren vidare till Spring Security
            return oidcUser;
        };
    }


}
