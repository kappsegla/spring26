package org.example.spring26;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping("/")
    public String open() {
        return "Anyone can access this endpoint";
    }

    @GetMapping("/admin")
    public String adminsOnly() {
        return "Only users with role ADMIN can access this endpoint.";
    }

    @GetMapping("/user")
    public String usersOnly(Authentication authentication) {
        var securityContext = SecurityContextHolder.getContext();
        var auth = securityContext.getAuthentication();

        return "Only users with role USER can access this endpoint. "
                + "You are: " + authentication.getName()
                + " Is authenticated: " + auth.isAuthenticated()
                + " Grants: " + auth.getAuthorities()
                + " Credentials: " + auth.getCredentials();
    }

}
