package org.example.spring26;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class HomeController {

    MessageSource messageSource;

    public HomeController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

//    @GetMapping("/home")
//    public String home(Model model) {
//        model.addAttribute("title", "Home");
//        model.addAttribute("message", "Hello from Spring MVC");
//        model.addAttribute("clock", new ClockBean());
//        return "home";
//    }

    @GetMapping("/jtehome")
    public String jtehome(Model model, Locale locale) {
        model.addAttribute("title", "JTE Demo");
        model.addAttribute("message", "Hello using JTE");
        model.addAttribute("localizer", new JteLocalizer(messageSource, locale));
        return "home";
    }

    @GetMapping("/mvhome")
    public ModelAndView mvhome() {
        var mv = new ModelAndView("home");
        mv.addObject("title", "MV Demo");
        mv.addObject("message", "Hello using MV");
        return mv;
    }
}
