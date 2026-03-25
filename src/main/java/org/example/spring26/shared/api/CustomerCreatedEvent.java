package org.example.spring26.shared.api;

public record CustomerCreatedEvent(Long customerId, String name) {
}
