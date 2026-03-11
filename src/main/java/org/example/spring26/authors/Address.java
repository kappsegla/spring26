package org.example.spring26.authors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String country;
    String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
