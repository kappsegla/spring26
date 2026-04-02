package org.example.spring26.order.infrastructure.config;

import org.example.spring26.order.application.OrderService;
import org.example.spring26.order.application.ports.in.OrderUseCase;
import org.example.spring26.order.application.ports.out.CustomerPort;
import org.example.spring26.order.application.ports.out.OrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderUseCase orderService(OrderRepositoryPort orderRepositoryPort,
                                     CustomerPort customerPort) {
        return new OrderService(orderRepositoryPort, customerPort);
    }
}
