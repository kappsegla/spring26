package org.example.spring26.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    BookService bookService;

    public BookController(BookService bookService) {
        log.info("BookController constructor");
        this.bookService = bookService;
    }

    @GetMapping("/books")
    List<Book> getBooks() {
        return List.of();
    }
}
