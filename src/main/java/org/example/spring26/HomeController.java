package org.example.spring26;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OAuth2User user, CsrfToken csrfToken) {
        if (user == null) {
            model.addAttribute("isAuthenticated", false);
            return "index";
        }
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("displayName", user.getAttribute("name"));
        model.addAttribute("csrfToken", csrfToken.getToken());
        return "index";
    }

    @GetMapping("/me")
    @ResponseBody
    public Map<String, Object> me(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }

    @GetMapping("/idtoken")
    @ResponseBody
    public String idToken(@AuthenticationPrincipal OidcUser user) {
        return user.getIdToken().getTokenValue();
    }

    @GetMapping("/profile")
    @ResponseBody
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
