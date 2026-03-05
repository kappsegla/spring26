package org.example.spring26.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/books/form")
    public String showForm() {
        return "form";
    }

    @PostMapping("/books/form")
    public String processForm(
            @RequestParam String name,
            @RequestParam String email, Model model) {
        //Validering
        //Save to db?
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "result";
    }
}
