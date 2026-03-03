package org.example.spring26.users;

import jakarta.validation.constraints.NotEmpty;

public record UserDTO(@NotEmpty String name) {
}
