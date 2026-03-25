package org.example.spring26.customer.application;

import org.example.spring26.customer.CustomerLookup;
import org.example.spring26.customer.domain.Customer;
import org.example.spring26.customer.domain.CustomerRepository;
import org.example.spring26.shared.api.CustomerCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerLookup {

    private final CustomerRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public CustomerService(CustomerRepository repository,
                           ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer createCustomer(String name) {
        Customer customer = new Customer(name);
        Customer saved = repository.save(customer);
        eventPublisher.publishEvent(new CustomerCreatedEvent(saved.getId(), saved.getName()));
        return saved;
    }

    @Override
    public Optional<CustomerLookup.CustomerDto> findById(Long id) {
        return repository.findById(id)
                .map(c -> new CustomerLookup.CustomerDto(c.getId(), c.getName()));
    }

    @Override
    public List<CustomerDto> listAllCustomers() {
        return repository.findAll().stream()
                .map(c -> new CustomerLookup.CustomerDto(c.getId(), c.getName())).toList();
    }
}
