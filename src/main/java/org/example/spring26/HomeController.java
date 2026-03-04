package org.example.spring26;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class HomeController {

    MessageSource messageSource;

    public HomeController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/home")
    public String jtehome(Model model, Locale locale) {
        model.addAttribute("title", "JTE Demo");
        model.addAttribute("message", "Hello using JTE");
        model.addAttribute("localizer", new JteLocalizer(messageSource, locale));
        return "home";
    }
}
