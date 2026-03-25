package org.example.spring26.order.infrastructure;

import org.example.spring26.order.application.ports.out.CustomerPort;
import org.example.spring26.order.application.ports.out.OrderRepositoryPort;
import org.example.spring26.order.application.ports.usecase.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderService orderService(OrderRepositoryPort orderRepositoryPort,
                                     CustomerPort customerPort) {
        return new OrderService(orderRepositoryPort, customerPort);
    }
}
