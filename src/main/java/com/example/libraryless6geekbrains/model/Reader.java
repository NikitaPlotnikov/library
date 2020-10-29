package com.example.libraryless6geekbrains.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Reader {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    @JsonIgnoreProperties("reader")
    private List<Book> readBook;

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", readBook=" + readBook +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id) &&
                Objects.equals(name, reader.name) &&
                Objects.equals(readBook, reader.readBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, readBook);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getReadBook() {
        return readBook;
    }

    public void setReadBook(List<Book> readBooks) {
        this.readBook = readBooks;
    }
}
