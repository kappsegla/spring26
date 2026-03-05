package org.example.spring26.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/books/form")
    public String showForm(Model model) {
        var userForm = new UserForm("", "");
        model.addAttribute("userForm", userForm);
        return "form";
    }

    @PostMapping("/books/form")
    public String processForm(UserForm userForm, Model model, RedirectAttributes redirectAttributes) {
        //Validering
        if (userForm.name() == null || userForm.name().isEmpty()) {
            model.addAttribute("userForm", userForm);
            model.addAttribute("nameError", "Please enter a name");
            return "form";
        }
        //Flash attributes are saved in our session
        redirectAttributes.addFlashAttribute("name", userForm.name());
        return "redirect:/books/thankyou";
    }

    @GetMapping("/books/thankyou")
    public String showThankYou() {
        //Session flash attributes are copied automatically into a Model
        //Flash attributes are now removed after rendering the new page
        return "thankyou";
    }
}
