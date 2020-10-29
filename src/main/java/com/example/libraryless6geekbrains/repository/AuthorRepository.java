package com.example.libraryless6geekbrains.repository;

import com.example.libraryless6geekbrains.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
