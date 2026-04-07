package org.example.spring26.customer.application;

import org.example.spring26.customer.CustomerCreatedEvent;
import org.example.spring26.customer.domain.Customer;
import org.example.spring26.customer.domain.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


//Run integration tests for individual application modules.
//Will load everything from the module with same package as this class.
@ApplicationModuleTest
@Testcontainers
class CustomerModuleIntegrationTest {


    @Container // Startar en Postgres-container för detta test
    @ServiceConnection // Kopplar automatiskt Spring Datasource till containern
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerService service;

    @Test
    void testFindAll() {
        // Arrange
        repository.save(new Customer("John Doe"));

        // Act
        var result = service.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testCreateCustomer(PublishedEvents events) {
        // Act - Skapa kunden på riktigt
        Customer result = service.createCustomer("Jane Doe");

        // Assert - Verifiera att objektet sparades och returnerades
        assertNotNull(result.getId()); // ID ska nu ha genererats av databasen
        assertEquals("Jane Doe", result.getName());

        // Assert - Verifiera eventet via Moduliths PublishedEvents
        var customerCreatedEvents = events.ofType(CustomerCreatedEvent.class)
                .matching(e -> e.name().equals("Jane Doe"));

        assertThat(customerCreatedEvents).hasSize(1);
    }

    @Test
    void testFindById() {
        // Arrange
        Customer savedCustomer = repository.save(new Customer("Alice"));
        Long id = savedCustomer.getId();

        // Act - Hämta via servicen
        var result = service.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals("Alice", result.name());
    }
}
