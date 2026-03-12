package org.example.spring26.books;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.profiles.active=test")
@Testcontainers
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    void shouldAUpdateBookTitle() {
        List<Book> books = bookRepository.findAll();
        Book book = books.get(0);

        int updatedCount = bookRepository.updateTitle(book.getId(), "New Title");

        assertThat(updatedCount).isEqualTo(1);
        Book updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        assertThat(updatedBook.getTitle()).isEqualTo("New Title");
    }

    @Test
    @Order(2)
    void shouldFindAllBooksFromSqlFile() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("The Great Gatsby", "1984");
    }
}
