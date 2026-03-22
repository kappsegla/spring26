package org.example.spring26;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PublicKeyCredentialUserEntityRepository users;

    public HomeController(PublicKeyCredentialUserEntityRepository users) {
        this.users = users;
    }

    @GetMapping("/")
    public String index(CsrfToken token, Model model, Authentication authentication) {
        model.addAttribute("csrfToken", token.getToken());
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            var user = users.findByUsername(authentication.getName());
            if (user != null) {
                model.addAttribute("displayName", user.getDisplayName());
            }
        }

        return "index";
    }

    @GetMapping("/add-passkey")
    public String addPasskey(CsrfToken token, Model model) {
        model.addAttribute("csrfToken", token.getToken());
        return "add-passkey";
    }
}
