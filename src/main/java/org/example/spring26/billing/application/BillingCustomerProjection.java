package org.example.spring26.billing.application;

import org.example.spring26.billing.domain.BillingCustomer;
import org.example.spring26.billing.domain.BillingCustomerRepository;
import org.example.spring26.customer.CustomerCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class BillingCustomerProjection {

    private final BillingCustomerRepository repository;
    Logger log = LoggerFactory.getLogger(BillingCustomerProjection.class);

    public BillingCustomerProjection(BillingCustomerRepository repository) {
        this.repository = repository;
    }

    @ApplicationModuleListener
    public void handle(CustomerCreatedEvent event) {
        BillingCustomer bc = new BillingCustomer(event.customerId(), event.name());
        repository.save(bc);
        log.info("Created Billing Customer " + bc.getName());
    }
}
