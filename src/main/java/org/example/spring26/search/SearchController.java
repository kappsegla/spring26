package org.example.spring26.search;

import org.example.spring26.books.Book;
import org.example.spring26.books.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class SearchController {


    BookService bookService;

    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam String query,
                                 @RequestParam(required = false, defaultValue = "20") int limit,
                                 @RequestParam(required = false, defaultValue = "ASC") Sort sort) {

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            books.add(new Book("Another " + query, i));
        }
        if (sort == Sort.DESC) {
            Collections.reverse(books);
        }
        return books;
    }
}
