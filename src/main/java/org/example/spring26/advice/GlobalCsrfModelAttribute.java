package org.example.spring26.advice;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = UsesCsrfForm.class)
public class GlobalCsrfModelAttribute {

    @ModelAttribute
    public void addCsrfToken(Model model, CsrfToken token) {
        if (token != null)
            model.addAttribute("csrf", token);
    }
}
