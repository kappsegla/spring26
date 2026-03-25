package org.example.spring26.billing.web;

import org.example.spring26.billing.domain.BillingCustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BillingController {

    private final BillingCustomerRepository repo;

    public BillingController(BillingCustomerRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/billing/customers")
    public String listBillingCustomers(Model model) {
        model.addAttribute("billingCustomers", repo.findAll());
        return "billing-customers";
    }
}
