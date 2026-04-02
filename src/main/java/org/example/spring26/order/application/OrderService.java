package org.example.spring26.order.application;

import org.example.spring26.order.application.ports.in.OrderUseCase;
import org.example.spring26.order.application.ports.out.CustomerPort;
import org.example.spring26.order.application.ports.out.OrderRepositoryPort;
import org.example.spring26.order.domain.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service är en infrastruktur‑annotation (komponentscanning, DI‑registrering).
public class OrderService implements OrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final CustomerPort customerPort;

    public OrderService(OrderRepositoryPort orderRepository,
                        CustomerPort customerPort) {
        this.orderRepository = orderRepository;
        this.customerPort = customerPort;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order createOrder(Long customerId) {
        Order order = Order.createNew(customerId);
        return orderRepository.save(order);
    }

    @Override
    public OrderDetails getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        var customer = customerPort.findById(order.getCustomerId());

        return new OrderDetails(
                order.getId(),
                customer.name(),
                order.getStatus()
        );
    }

    @Override
    public List<CustomerInfo> getAvailableCustomers() {
        return customerPort.findAll().stream()
                .map(it -> new CustomerInfo(it.id(), it.name()))
                .toList();
    }
}
