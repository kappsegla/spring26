package org.example.spring26.profile;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {


    private final PublicKeyCredentialUserEntityRepository publicKeyCredentialUserEntityRepository;
    private final UserCredentialRepository userCredentialRepository;

    public ProfileController(PublicKeyCredentialUserEntityRepository publicKeyCredentialUserEntityRepository,
                             UserCredentialRepository userCredentialRepository) {
        this.publicKeyCredentialUserEntityRepository = publicKeyCredentialUserEntityRepository;
        this.userCredentialRepository = userCredentialRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication, org.springframework.security.web.csrf.CsrfToken token) {
        // Hämta användarnamnet från den inloggade sessionen
        String username = authentication.getName();

        var userInfo = publicKeyCredentialUserEntityRepository.findByUsername(username);
        // Hämta alla registrerade passkeys för denna användare
        var keys = userCredentialRepository.findByUserId(userInfo.getId());

        model.addAttribute("username", username);
        model.addAttribute("keys", keys);
        model.addAttribute("csrfToken", token.getToken());

        return "profile"; // Detta mappar till profile.jte
    }
}
