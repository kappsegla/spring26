package org.example.spring26.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerLookup {

    Optional<CustomerDto> findById(Long id);

    List<CustomerDto> listAllCustomers();

    record CustomerDto(Long id, String name) {
    }
}
