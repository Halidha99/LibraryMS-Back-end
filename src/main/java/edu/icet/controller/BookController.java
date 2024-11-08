package edu.icet.controller;

import edu.icet.dto.Book;
import edu.icet.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/get-book")
    public List<Book> getBook() {
        return bookService.getAll();
    }

    @PostMapping("/add-book")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @GetMapping("/search-by-id/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookService.searchBookById(id);
    }


    @GetMapping("/search-by-name/{name}")
    public List<Book> searchByName(@PathVariable String bookName) {
        return bookService.searchByName(bookName);
    }

    @DeleteMapping("/delete-by-id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/update-book")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBook(@RequestBody Book book) {
        bookService.updateBookById(book);
    }
}
