package org.example.spring26.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        log.info("BookController constructor");
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        log.info("Real service class is: {} ", this.bookService.getClass().getName());
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestParam String title) {
        return bookService.saveBook(title);
    }

    /**
     * Demonstrate transaction rollback.
     * URL: /books/bulk-update?ids=1,2&newTitle=Updated&fail=true
     */
    @PutMapping("/bulk-update")
    public String bulkUpdate(
            @RequestParam List<Long> ids,
            @RequestParam String newTitle,
            @RequestParam(defaultValue = "false") boolean fail) {
        bookService.updateTitlesInTransaction(ids, newTitle, fail);
        return "Bulk update successful!";
    }
}
