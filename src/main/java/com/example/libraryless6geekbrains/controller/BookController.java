package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Book;
import com.example.libraryless6geekbrains.model.Reader;
import com.example.libraryless6geekbrains.repository.BookRepository;
import com.example.libraryless6geekbrains.repository.ReaderRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public BookController(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks (){
        return bookRepository.findAll();
    }

    @PostMapping("/book")
    public Book addBook(@RequestBody Book input ){
        return bookRepository.save(input);
    }

    @GetMapping("/book/reader")
    public Reader getReaderOfBook(@RequestParam Long id){
        return readerRepository.findByReadBookId(id);
    }
}
