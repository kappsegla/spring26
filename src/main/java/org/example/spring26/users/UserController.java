package org.example.spring26.users;

import org.example.spring26.orders.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users/{userId}/orders/{orderId}")
    public List<Order> getUserOrder(@PathVariable String userId, @PathVariable String orderId){
        return List.of(new Order(orderId, userId));
    }
}
