package org.example.spring26.order.domain;

import java.time.Instant;

public class Order {

    private final Long id;
    private final Long customerId;
    private final Instant createdAt;
    private String status;

    public Order(Long id, Long customerId, Instant createdAt, String status) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static Order createNew(Long customerId) {
        return new Order(null, customerId, Instant.now(), "CREATED");
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
