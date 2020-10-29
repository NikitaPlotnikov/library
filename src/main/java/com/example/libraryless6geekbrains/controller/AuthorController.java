package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Author;
import com.example.libraryless6geekbrains.model.Book;
import com.example.libraryless6geekbrains.model.Reader;
import com.example.libraryless6geekbrains.repository.AuthorRepository;
import com.example.libraryless6geekbrains.repository.BookRepository;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/author")
    public List<Author> getAllAuthors (){
        return authorRepository.findAll();
    }
    
    @PostMapping("/author")
    public Author addAuthor(@RequestBody Author input ){
        input.setWriteBook(new ArrayList<>());
        return authorRepository.save(input);
    }

    @GetMapping("/author/addbook")
    public Author addBookToAuthor(@RequestParam Long bookId,Long authorId){
        Book book = bookRepository.getOne(bookId);
        Author author = authorRepository.getOne(authorId);
        book.setAuthor(author);
        author.getWriteBook().add(book);
        bookRepository.save(book);
        authorRepository.save(author);
        return author;
    }

    @GetMapping("/author/books")
    public List<Book> getBooksByAuthor(@RequestParam Long id){
        return bookRepository.findAllByAuthorId(id);
    }

    @GetMapping("/author/books/readers")
    public List<Reader> getReadersBooksByAuthor(@RequestParam Long id){
        Author author = authorRepository.getOne(id);
        List<Book> books = author.getWriteBook();
        ArrayList<Reader> readers = new ArrayList<>();
        for (Book b: books){
            if (!(readers.contains(b.getReader())))
            readers.add(b.getReader());
        }
        return readers;
    }
}
