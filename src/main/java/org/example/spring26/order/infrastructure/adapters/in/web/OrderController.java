package org.example.spring26.order.infrastructure.adapters.in.web;

import org.example.spring26.customer.CustomerLookup;
import org.example.spring26.order.application.ports.in.OrderUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final CustomerLookup customerLookup;

    public OrderController(OrderUseCase orderUseCase, CustomerLookup customerLookup) {
        this.orderUseCase = orderUseCase;
        this.customerLookup = customerLookup;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderUseCase.findAll());
        model.addAttribute("customers", customerLookup.listAllCustomers());
        return "orders";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        return "orders";
    }

    @PostMapping
    public String createOrder(@RequestParam Long customerId) {
        orderUseCase.createOrder(customerId);
        return "redirect:/orders";
    }

    @GetMapping("/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        var details = orderUseCase.getOrderDetails(id);
        model.addAttribute("order", details);
        return "order-details";
    }
}
