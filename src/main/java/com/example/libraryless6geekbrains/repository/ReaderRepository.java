package com.example.libraryless6geekbrains.repository;

import com.example.libraryless6geekbrains.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader,Long> {
    Reader findByReadBookId(Long bookId);
}
