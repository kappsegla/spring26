package org.example.spring26.order.application.ports.in;

import org.example.spring26.order.domain.Order;

import java.util.List;

public interface OrderUseCase {
    List<Order> findAll();

    Order createOrder(Long customerId);

    OrderDetails getOrderDetails(Long orderId);

    List<CustomerInfo> getAvailableCustomers();

    record OrderDetails(Long orderId, String customerName, String status) {
    }

    record CustomerInfo(Long id, String name) {
    }
}
