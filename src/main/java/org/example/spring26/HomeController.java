package org.example.spring26;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
