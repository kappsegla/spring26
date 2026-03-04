package org.example.spring26;

import java.time.LocalDateTime;

public class ClockBean {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
