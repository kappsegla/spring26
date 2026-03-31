package org.example.spring26.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import java.util.Collections;
import java.util.Map;


@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    /*
    On the server side, the goal is to make your API "SPA-aware." By default, Spring Security assumes every request
    comes from a browser address bar, so it tries to be helpful by sending a 302 Redirect to the login page.

    For a JavaScript fetch call, a redirect to a 3rd-party OAuth provider (Google) causes a CORS error.
    Instead, we want the server to send a 401 Unauthorized status code for anything under /api/**.
    */


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.oidcUserService())
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error", "/cars/**", "/htmx.min.js", "/oat.min.js", "/oat.min.css").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        // 1. Handle "Not Logged In" (401 for APIs, Redirect for Others)
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                // This is the most modern/standard way to target a path pattern
                                request -> request.getServletPath().startsWith("/api/")
                        )
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .build();
    }


    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return userRequest -> {
            // 1. Låt Spring hämta ID Token + UserInfo
            OidcUser oidcUser = delegate.loadUser(userRequest);

            // This is JUST the "Direct" info from the JWT
            Map<String, Object> idTokenClaims = oidcUser.getIdToken().getClaims();

            // This is the "Merged" info (ID Token + Extra Call)
            Map<String, Object> allAttributes = oidcUser.getAttributes();

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

    //Alternative implementation that doesn't load extra profile info
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserServiceLight() {
        return userRequest -> {
            // We DO NOT call delegate.loadUser(userRequest) here.
            // Instead, we just grab the ID Token already in the request.
            OidcIdToken idToken = userRequest.getIdToken();

            // We wrap it in a DefaultOidcUser.
            // This contains only what was "pushed" during login (email, sub, etc.)
            return new DefaultOidcUser(Collections.emptyList(), idToken);
        };
    }


}
