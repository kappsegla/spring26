package org.example.spring26.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

//    @Bean
//    public DirectExchange directExchange() {
//        // The name must match exactly: 'customer.CustomerCreatedEvent'
//        return new DirectExchange("customer.CustomerCreatedEvent");
//    }

    @Bean
    public TopicExchange topicEventsExchange() {
        return new TopicExchange("customer.CustomerCreatedEvent");
    }

//    @Bean
//    FanoutExchange fanoutExchange() {
//        return new FanoutExchange("customer.CustomerCreatedEvent");
//    }
}
