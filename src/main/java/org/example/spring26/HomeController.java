package org.example.spring26;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<?> index(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return ResponseEntity.status(302)
                    .header("Location", "/login")
                    .build();
        }
        return ResponseEntity.ok("Hello " + user.getAttribute("name"));
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }

    @GetMapping("/idtoken")
    public String idToken(@AuthenticationPrincipal OidcUser user) {
        return user.getIdToken().getTokenValue();
    }

    @GetMapping("/profile")
    public Map<String, Object> getProfile(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client) {
        // 1. Get the URL from the registration
        String userInfoUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        // 2. Create the RestClient (usually you'd inject this as a Bean)
        RestClient restClient = RestClient.create();

        // 3. Make the call using the modern fluent API
        return restClient.get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + client.getAccessToken().getTokenValue())
                .retrieve()
                .body(Map.class); // No need for .block() like WebClient!
    }
}
