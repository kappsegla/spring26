package org.example.spring26

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KotlinController {

    @GetMapping("/kotlin")
    fun kotlin() = "Hello from Kotlin!"
}
