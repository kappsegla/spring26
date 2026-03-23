package org.example.spring26.devicelink;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
public class DeviceLinkController {

    private final DeviceLinkTokenRepository repo;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public DeviceLinkController(DeviceLinkTokenRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/device-link/create")
    public DeviceLinkResponse createLink(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String token = UUID.randomUUID().toString();
        DeviceLinkToken t = new DeviceLinkToken();
        t.setToken(token);
        t.setUsername(authentication.getName());
        t.setExpiresAt(Instant.now().plusSeconds(300));
        t.setUsed(false);
        repo.save(t);

        String url = "http://localhost:8080/device-link?token=" + token;
        DeviceLinkResponse resp = new DeviceLinkResponse();
        resp.setUrl(url);
        return resp;
    }

    @GetMapping("/device-link")
    public void linkDevice(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DeviceLinkToken t = repo.findById(token)
                .filter(x -> !x.isUsed())
                .filter(x -> x.getExpiresAt().isAfter(Instant.now()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        t.setUsed(true);
        repo.save(t);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        t.getUsername(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_TEMP"))
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        response.sendRedirect("/add-passkey");
    }

    public static class DeviceLinkResponse {
        private String url;

        public DeviceLinkResponse() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
