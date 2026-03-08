package org.example.spring26.books;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BulkUpdateException extends RuntimeException {
    public BulkUpdateException(String message) {
        super(message);
    }
}
