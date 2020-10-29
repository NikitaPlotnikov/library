package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    void addBookTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/book")
                .content("{\"id\":null,\"title\":\"Metro 2033\",\"author\":null,\"reader\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseBookAsString = mvcResult.getResponse().getContentAsString();
        Book book = objectMapper.readerFor(Book.class).readValue(responseBookAsString, Book.class);
        Assertions.assertNotNull(book.getId());
        Assertions.assertEquals("Metro 2033", book.getTitle());
    }

    @Test
    void getAllBooksTest() throws Exception {
        addBook();
        MvcResult mvcResult = this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseBooksAsString = mvcResult.getResponse().getContentAsString();
        Book[] books = objectMapper.readerFor(Book.class).readValue(responseBooksAsString,Book[].class);
        ArrayList<Book> bookArrayList = new ArrayList<>();
        Collections.addAll(bookArrayList,books);
        Assertions.assertEquals(bookArrayList.size(), 2);
    }

    private void addBook() throws Exception {
        this.mockMvc.perform(post("/book")
                .content("{\"id\":null,\"title\":\"Metro 2033\",\"author\":null,\"reader\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(post("/book")
                .content("{\"id\":null,\"title\":\"Metro 2034\",\"author\":null,\"reader\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
}