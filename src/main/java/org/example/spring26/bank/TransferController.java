package org.example.spring26.bank;

import org.example.spring26.advice.UsesCsrfForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@UsesCsrfForm
public class TransferController {

    @GetMapping("/transfer")
    public String showTransferForm(Model model) {
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transferMoney(
            @RequestParam String toAccount,
            @RequestParam int amount,
            Model model,
            Authentication authentication
    ) {
        model.addAttribute("toAccount", toAccount);
        model.addAttribute("amount", amount);
        return "transferSuccess";
    }
}
