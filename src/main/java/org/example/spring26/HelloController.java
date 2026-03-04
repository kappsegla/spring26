package org.example.spring26;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

//@Controller
//@ResponseBody
@RestController
public class HelloController {

    @GetMapping("/")
    public Hello sayHello() {
        return new Hello("Kalle", LocalDateTime.now());
    }

    @PostMapping("/hellos")
    public void add(@RequestBody Hello hello) {

    }

    @GetMapping("/hej")
    public ResponseEntity<Void> haj() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("https://iths.se/"))
                .build();
    }

    @PostMapping("/")
    public String sayHelloPost() {
        return "Post hello";
    }

    record Hello(String name, LocalDateTime time) {
    }

//    @GetMapping("/home")
//    public String home() {
//        return """
//                <!DOCTYPE html>
//                <html lang="en">
//                <head>
//                    <meta charset="UTF-8">
//                    <title>Title</title>
//                </head>
//                <body>
//                <p>Hello Home</p>
//                """
//                + LocalDateTime.now() +
//                """
//                        </body>
//                        </html>
//                        """;
//    }

}
