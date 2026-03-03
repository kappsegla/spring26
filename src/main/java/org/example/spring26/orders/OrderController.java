package org.example.spring26.orders;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @PostMapping("/orders/bulk")
    public int createBulkOrders(@RequestBody List<OrderDTO> orders) {
        return orders.size();
    }
}
