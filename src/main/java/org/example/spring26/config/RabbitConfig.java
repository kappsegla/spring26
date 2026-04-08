package org.example.spring26.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange("customer.events");
//    }

    @Bean
    public TopicExchange topicEventsExchange() {
        return new TopicExchange("customer.events");
    }

//    @Bean
//    FanoutExchange fanoutExchange() {
//        return new FanoutExchange("customer.events");
//    }
}
