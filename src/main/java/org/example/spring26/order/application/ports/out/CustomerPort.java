package org.example.spring26.order.application.ports.out;

import org.example.spring26.customer.CustomerLookup;

import java.util.List;
import java.util.Optional;

public interface CustomerPort {
    Optional<CustomerInfo> findById(Long id);

    List<CustomerLookup.CustomerDto> findAll();

    record CustomerInfo(Long id, String name) {
    }
}
