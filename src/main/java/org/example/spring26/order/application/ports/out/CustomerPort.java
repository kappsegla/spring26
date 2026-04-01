package org.example.spring26.order.application.ports.out;

import java.util.List;

public interface CustomerPort {
    CustomerInfo findById(Long id);

    List<CustomerInfo> findAll();

    record CustomerInfo(Long id, String name) {
    }
}
