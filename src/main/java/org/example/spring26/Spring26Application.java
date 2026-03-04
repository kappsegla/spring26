package org.example.spring26;

import org.example.spring26.books.Book;
import org.example.spring26.books.BookRepository;
import org.example.spring26.books.BookService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Spring26Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring26Application.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(BookRepository bookRepository) {
        return args -> {
            if( bookRepository.count() == 0 ) {
                Book book = new Book();
                book.setTitle("Book 1");
                bookRepository.save(book);
                book = new Book();
                book.setTitle("Book 2");
                bookRepository.save(book);
            }
        };
    }
}
