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
}
