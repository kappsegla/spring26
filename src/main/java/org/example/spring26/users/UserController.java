package org.example.spring26.users;

import jakarta.validation.Valid;
import org.example.spring26.orders.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users/{userId}/orders/{orderId}")
    public List<Order> getUserOrder(@PathVariable String userId, @PathVariable String orderId) {
        return List.of(new Order(orderId, userId));
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(1234)
                .toUri();

        return ResponseEntity.created(location).build();


    }
}
