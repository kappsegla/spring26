package org.example.spring26.customer;

import org.springframework.modulith.events.Externalized;

@Externalized("customer.events::customer.CustomerCreatedEvent")
public record CustomerCreatedEvent(Long customerId, String name) {
}
