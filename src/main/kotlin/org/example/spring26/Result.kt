package org.example.spring26

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

sealed interface Result<out T>
data class Ok<T>(val value: T) : Result<T>
object NotFound : Result<Nothing>
data class InvalidInput(val reason: String) : Result<Nothing>
data class Forbidden(val message: String) : Result<Nothing>

// Extension function som nu kan användas av ALLA controllers
fun <T> Result<T>.toResponseEntity(): ResponseEntity<Any> = when (this) {
    is Ok -> ResponseEntity.ok(this.value as Any)
    is NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found")
    is InvalidInput -> ResponseEntity.badRequest().body(this.reason)
    is Forbidden -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(this.message)
}
