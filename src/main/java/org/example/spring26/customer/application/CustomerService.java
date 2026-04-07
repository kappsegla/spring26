package org.example.spring26.customer.application;

import org.example.spring26.customer.CustomerCreatedEvent;
import org.example.spring26.customer.CustomerLookup;
import org.example.spring26.customer.domain.Customer;
import org.example.spring26.customer.domain.CustomerRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public Customer createCustomer(String name) {
        Customer customer = new Customer(name);
        Customer saved = repository.save(customer);
        eventPublisher.publishEvent(new CustomerCreatedEvent(saved.getId(), saved.getName()));
        return saved;
    }

    @Override
    public CustomerDto findById(Long id) {
        return repository.findById(id)
                .map(c -> new CustomerLookup.CustomerDto(c.getId(), c.getName()))
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public List<CustomerDto> listAllCustomers() {
        return repository.findAll().stream()
                .map(c -> new CustomerLookup.CustomerDto(c.getId(), c.getName())).toList();
    }
}
