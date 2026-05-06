package org.example.spring26.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);


    public List<Book> allBooks() {
        return List.of(new Book("The Hobbit", 1), new Book("The Lord of the Rings", 2));
    }

    public Book oneBook(int id) {
        if (id != 1)
            throw new BookNotFound("Invalid book id");
        return new Book("The Hobbit", 1);
    }

    public void deleteBook(int id) {

    }

    public void createBook(Book book) {

    }
}
