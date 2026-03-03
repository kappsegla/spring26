package org.example.spring26.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    public BookService() {
        log.info("BookService constructor");
    }

    public Book getBook(String id) {
        if( id == null || id.isEmpty() || Integer.parseInt(id) < 0 )
            throw new BookNotFoundException("Bok med id " + id + " hittades inte");
        return new Book("Book Title", Integer.parseInt(id));
    }
}
