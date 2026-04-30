package org.example.spring26.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
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

    public void createBook(Book book) {
        var result = RestClient.create().get().uri(URI.create("http://localhost:8080/books"))
                .retrieve().onStatus(HttpStatusCode::is4xxClientError,
                        ((request, response) -> {
                            //Handle 4xx errors

                        }
                        ))
                .body(List.class);
    }

    public void deleteBook(int id) {

    }
}
