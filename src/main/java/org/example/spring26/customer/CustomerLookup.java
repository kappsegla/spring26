package org.example.spring26.customer;

import java.util.List;

public interface CustomerLookup {

    CustomerDto findById(Long id);

    List<CustomerDto> listAllCustomers();

    record CustomerDto(Long id, String name) {
    }
}
