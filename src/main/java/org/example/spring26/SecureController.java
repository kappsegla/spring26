package org.example.spring26;

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
    public String usersOnly() {
        return "Only users with role USER can access this endpoint.";
    }

}
