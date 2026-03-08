package org.example.spring26;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    MessageSource messageSource;

    public HomeController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    public String jtehome(Model model, Locale locale) {
        model.addAttribute("page", new HomePage("Welcome", "Hello using JTE", true));
        model.addAttribute("tasks", List.of("Java", "Kotlin", "JavaScript"));
        model.addAttribute("visible", true);
        model.addAttribute("localizer", new JteLocalizer(messageSource, locale));
        return "home";
    }
}
