package org.example.spring26.books;

//@RestControllerAdvice
//public class BookNotFoundExceptionHandler {
//
//    //@ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(BookNotFound.class)
//    public ResponseEntity<ProblemDetail> handleException(BookNotFound ex) {
//        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
//        problem.setProperty("timestamp", Instant.now().toString());
//        problem.setTitle("Unkown book id");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
//    }
//}
