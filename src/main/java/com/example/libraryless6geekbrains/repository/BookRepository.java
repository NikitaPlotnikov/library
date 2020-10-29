package com.example.libraryless6geekbrains.repository;

import com.example.libraryless6geekbrains.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthorId(Long authorId);
    List<Book> findAllByReaderId(Long readerId);
 }
