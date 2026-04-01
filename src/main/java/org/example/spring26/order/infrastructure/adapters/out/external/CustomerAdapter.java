package org.example.spring26.order.infrastructure.adapters.out.external;

import org.example.spring26.customer.CustomerLookup;
import org.example.spring26.order.application.ports.out.CustomerPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerAdapter implements CustomerPort {

    private final CustomerLookup customerLookup;

    public CustomerAdapter(CustomerLookup customerLookup) {
        this.customerLookup = customerLookup;
    }

    @Override
    public CustomerInfo findById(Long id) {
        var customer = customerLookup.findById(id);
        return new CustomerInfo(customer.id(), customer.name());
    }

    @Override
    public List<CustomerInfo> findAll() {
        return customerLookup.listAllCustomers().stream()
                .map(customer -> new CustomerInfo(customer.id(), customer.name()))
                .toList();
    }
}
