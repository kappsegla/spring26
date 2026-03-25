package org.example.spring26.order.infrastructure.adapters.out.external;

import org.example.spring26.customer.CustomerLookup;
import org.example.spring26.order.application.ports.out.CustomerPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerAdapter implements CustomerPort {

    private final CustomerLookup customerLookup;

    public CustomerAdapter(CustomerLookup customerLookup) {
        this.customerLookup = customerLookup;
    }

    @Override
    public Optional<CustomerInfo> findById(Long id) {
        return customerLookup.findById(id)
                .map(customer -> new CustomerInfo(customer.id(), customer.name()));
    }

    @Override
    public List<CustomerLookup.CustomerDto> findAll() {
        return customerLookup.listAllCustomers();
    }
}
