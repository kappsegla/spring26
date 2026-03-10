package org.example.spring26;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

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

    @GetMapping("/xss")
    @ResponseBody
    public String xss() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>test</title>
                </head>
                <body>
                <h1>Home page</h1>
                """ +
                HtmlUtils.htmlEscape("<script>alert('Hej');</script>")
                +
                """
                        </body>
                        </html>
                        """;
    }
}
