package org.example.spring26;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
