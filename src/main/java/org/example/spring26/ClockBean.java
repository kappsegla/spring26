package org.example.spring26;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClockBean {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
