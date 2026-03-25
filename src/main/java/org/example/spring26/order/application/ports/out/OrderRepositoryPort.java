package org.example.spring26.order.application.ports.out;

import org.example.spring26.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    List<Order> findAll();

    Order save(Order order);

    Optional<Order> findById(Long id);
}
