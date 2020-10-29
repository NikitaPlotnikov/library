package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Author;
import com.example.libraryless6geekbrains.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetBooksByAuthorTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddModel addModel;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBooksByAuthorTest() throws Exception {
        Author authorRemarque = addModel.addAuthor("Erich Maria Remarque");
        Book bookThreeComrades = addModel.addBook("Three Comrades");
        Book bookArchOfTriumph = addModel.addBook("Arch of Triumph");
        Book bookAllQuietOnTheWesternFront = addModel.addBook("All Quiet on the Western Front");

        Long authorRemarqueId = authorRemarque.getId();
        Long bookThreeComradesId = bookThreeComrades.getId();
        Long bookArchOfTriumphId = bookArchOfTriumph.getId();
        Long bookAllQuietOnTheWesternFrontId = bookAllQuietOnTheWesternFront.getId();

        addBookToAuthorTest(bookThreeComradesId,authorRemarqueId);
        addBookToAuthorTest(bookArchOfTriumphId,authorRemarqueId);
        addBookToAuthorTest(bookAllQuietOnTheWesternFrontId,authorRemarqueId);

        MvcResult mvcResult = this.mockMvc.perform(get("/author/books?id=" + authorRemarqueId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        Book[] responseBooks = objectMapper.readerFor(Book.class).readValue(responseReaderAsString, Book[].class);
        ArrayList<Book> bookArrayList = new ArrayList<>();
        Collections.addAll(bookArrayList,responseBooks);
        Assertions.assertEquals(3,bookArrayList.size());
        Assertions.assertEquals("Three Comrades", bookArrayList.get(0).getTitle());
        Assertions.assertEquals("Arch of Triumph", bookArrayList.get(1).getTitle());
        Assertions.assertEquals("All Quiet on the Western Front", bookArrayList.get(2).getTitle());
        Assertions.assertEquals("Erich Maria Remarque",bookArrayList.get(0).getAuthor().getName());
        Assertions.assertEquals("Erich Maria Remarque",bookArrayList.get(1).getAuthor().getName());
        Assertions.assertEquals("Erich Maria Remarque",bookArrayList.get(2).getAuthor().getName());
    }

    private void addBookToAuthorTest(Long bookId,Long authorId) throws Exception {
        this.mockMvc.perform(get("/author/addbook?bookId=" + bookId + "&authorId=" + authorId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
