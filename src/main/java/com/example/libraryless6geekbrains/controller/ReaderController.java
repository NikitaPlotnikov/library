package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Book;
import com.example.libraryless6geekbrains.model.Reader;
import com.example.libraryless6geekbrains.repository.BookRepository;
import com.example.libraryless6geekbrains.repository.ReaderRepository;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReaderController {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public ReaderController(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @GetMapping("/reader")
    public List<Reader> getAllReaders (){
        return readerRepository.findAll();
    }

    @PostMapping("/reader")
    public Reader addReader(@RequestBody Reader input ){
        input.setReadBook(new ArrayList<>());
        return readerRepository.save(input);
    }

    @GetMapping("/reader/addbook")
    public Reader addBookToReader(@RequestParam Long bookId,Long readerId){
        Book book = bookRepository.getOne(bookId);
        Reader reader = readerRepository.getOne(readerId);
        reader.getReadBook().add(book);
        book.setReader(reader);
        readerRepository.save(reader);
        bookRepository.save(book);
        return reader;
    }

    @GetMapping("/reader/allbooks")
    public List<Book> allBooksOfReader(@RequestParam Long id){
        return bookRepository.findAllByReaderId(id);
    }
}