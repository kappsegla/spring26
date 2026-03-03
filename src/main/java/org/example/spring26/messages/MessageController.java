package org.example.spring26.messages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @PostMapping("/messages")
    public ResponseEntity<String> createMessage(
            @RequestBody MessageDTO message,
            @RequestParam Priority priority) {
        
        String responseText = "Mottaget meddelande: " + message.text() + " med prioritet " + priority;
        
        if (priority == Priority.HIGH) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseText);
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseText);
        }
    }
}
