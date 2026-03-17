package org.example.spring26

import org.springframework.stereotype.Service

@Service
class KotlinService {
    fun getUserById(id: Long): Result<User> {
        return when {
            id <= 0 -> InvalidInput("User ID must be positive")
            id == 1L -> Forbidden("You don't have access to this user information")
            id == 1001L -> Ok(User(1001L, "Laptop Pro"))
            else -> NotFound
        }
    }
}
