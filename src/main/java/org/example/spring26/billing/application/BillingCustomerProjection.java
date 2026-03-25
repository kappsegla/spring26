package org.example.spring26.billing.application;

import org.example.spring26.billing.domain.BillingCustomer;
import org.example.spring26.billing.domain.BillingCustomerRepository;
import org.example.spring26.shared.api.CustomerCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BillingCustomerProjection {

    private final BillingCustomerRepository repository;

    public BillingCustomerProjection(BillingCustomerRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handle(CustomerCreatedEvent event) {
        BillingCustomer bc = new BillingCustomer(event.customerId(), event.name());
        repository.save(bc);
    }
}
