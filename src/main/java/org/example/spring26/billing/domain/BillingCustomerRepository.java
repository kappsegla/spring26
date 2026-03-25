package org.example.spring26.billing.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingCustomerRepository extends JpaRepository<BillingCustomer, Long> {
}
