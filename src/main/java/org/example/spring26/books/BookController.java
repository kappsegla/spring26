package org.example.spring26.books;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.net.URI;
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
    List<Book> getBook() {
        return bookService.allBooks();
    }

    @Operation(summary = "Get one book",
            description = "Returns a book by id",
            tags = {"books"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book found",
                            content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found",
                            content = @Content)
            })
    @GetMapping("/books/{id}")
    Book getBookById(@PathVariable int id) {
        return bookService.oneBook(id);
    }

    @PostMapping("/books")
    ResponseEntity<Void> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        return ResponseEntity.created(URI.create("/books/" + book.id())).build();
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/limits")
    ResponseEntity<String> limits() {
        var result = RestClient.create().get()
                .uri(URI.create("https://openrouter.ai/api/v1/key"))
                .header("Authorization", "Bearer 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")
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }
}
