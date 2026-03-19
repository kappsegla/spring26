package org.example.spring26.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SaldoController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @CrossOrigin(
            origins = "http://evil.localhost:8081",
            allowCredentials = "true"
    )
    @GetMapping("/balance")
    public Map<String, Object> getBalance(Authentication authentication) {
        log.info("getBalance was called by someone");
        return Map.of("user", authentication != null ? authentication.getName() : "anonymous", "balance", 1234);
    }

    @PutMapping("/update")
    public String updateBalance() {
        log.info("updateBalance was called by someone");
        return "Balance was updated";
    }
}
