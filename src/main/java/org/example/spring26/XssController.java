package org.example.spring26;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class XssController {

    @GetMapping("/xss/vulnerable")
    public String vulnerable(@RequestParam(value = "name", defaultValue = "Guest") String name, Model model) {
        model.addAttribute("name", name);
        return "xss-vulnerable";
    }

    @GetMapping("/xss/protected")
    public String protectedXss(@RequestParam(value = "name", defaultValue = "Guest") String name, Model model) {
        model.addAttribute("name", name);
        return "xss-protected";
    }
}
