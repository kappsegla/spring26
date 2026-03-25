package org.example.spring26.billing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BillingCustomer {

    @Id
    private Long id;

    private String name;

    protected BillingCustomer() {
    }

    public BillingCustomer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
