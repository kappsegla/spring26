package org.example.spring26

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class KotlinController(private val kotlinService: KotlinService) {

    @GetMapping("/kotlin/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<Any> {
        return kotlinService.getUserById(id).toResponseEntity()
    }
}
