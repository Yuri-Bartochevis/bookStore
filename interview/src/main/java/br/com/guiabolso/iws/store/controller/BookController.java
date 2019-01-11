package br.com.guiabolso.iws.store.controller;


import br.com.guiabolso.iws.store.model.response.BookResponse;
import br.com.guiabolso.iws.store.model.response.BooksResponse;
import br.com.guiabolso.iws.store.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BookController {
    public static final Logger lOGGER = LoggerFactory.getLogger(BookController.class);
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    public ResponseEntity<BooksResponse> allBooks() {
        return ResponseEntity.ok(new BooksResponse(bookService.allBooks()));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookResponse> findBook(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(new BookResponse(bookService.findByid(id)));
        } catch (RuntimeException e) {
            lOGGER.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}