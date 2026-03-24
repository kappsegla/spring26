package org.example.spring26.webauthn;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.SecureRandom;
import java.util.List;

@Controller
public class SignupController {

    private final PublicKeyCredentialUserEntityRepository users;
    private final SecureRandom random = new SecureRandom();

    public SignupController(PublicKeyCredentialUserEntityRepository users) {
        this.users = users;
    }

    @GetMapping("/signup")
    String signup(org.springframework.security.web.csrf.CsrfToken token, Model model) {
        model.addAttribute("csrfToken", token.getToken());
        return "signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@RequestBody SignupRequest req, HttpServletRequest request, HttpServletResponse response) {
        if (users.findByUsername(req.username) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }

        byte[] idBytes = new byte[32];
        random.nextBytes(idBytes);

        UserEntity userEntity = new UserEntity(
                new Bytes(idBytes),
                req.username,
                req.displayName
        );

        users.save(userEntity);

        // Automatically authenticate user after signup
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userEntity.getName(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // Sätt i SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        // Spara i sessionen via SecurityContextRepository
        SecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        repo.saveContext(context, request, response);

        return ResponseEntity.ok().build();
    }

    public static class SignupRequest {
        private String username;
        private String displayName;

        public SignupRequest() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
}
