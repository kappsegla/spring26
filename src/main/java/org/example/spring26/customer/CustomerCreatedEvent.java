package org.example.spring26.customer;

public record CustomerCreatedEvent(Long customerId, String name) {
}
