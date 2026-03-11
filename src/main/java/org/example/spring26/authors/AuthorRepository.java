package org.example.spring26.authors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {

    Author findByFirstName(String firstName);

    Optional<Author> findAuthorByFirstName(String firstName);

    List<Author> findByAddressCity(String addressCity);

    Page<Author> findAllBy(Pageable pageable);

}
