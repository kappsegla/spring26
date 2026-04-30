package org.example.spring26.books;

import java.time.Instant;

public class BookError {
    public Instant timestamp;
    public int status;
    public String error;

    public BookError(Instant timestamp, int status, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }
}
