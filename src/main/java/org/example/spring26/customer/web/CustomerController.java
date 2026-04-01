package org.example.spring26.customer.web;

import org.example.spring26.customer.CustomerLookup;
import org.example.spring26.customer.application.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", service.findAll());
        return "customers";
    }

    @GetMapping("{id}")
    @ResponseBody
    public CustomerLookup.CustomerDto listCustomers(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/new")
    public String showCreateForm() {
        return "customer-create";
    }

    @PostMapping
    public String createCustomer(@RequestParam String name) {
        service.createCustomer(name);
        return "redirect:/customers";
    }
}
