package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Book;
import com.example.libraryless6geekbrains.model.Reader;
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
public class AllBooksOfReaderTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddModel addModel;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void allBooksOfReaderTest() throws Exception {
        Reader readerNikita = addModel.addReader("Nikita");
        Book bookRPO = addModel.addBook("Ready Player One");
        Book bookDune = addModel.addBook("Dune");
        Long readerNikitaId = readerNikita.getId();
        Long bookRPOId = bookRPO.getId();
        Long bookDuneId = bookDune.getId();
        addBookToReader(bookRPOId,readerNikitaId);
        addBookToReader(bookDuneId,readerNikitaId);
        MvcResult mvcResult = this.mockMvc.perform(get("/reader/allbooks?id=" + readerNikitaId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        Book[] responseBooks = objectMapper.readerFor(Book.class).readValue(responseReaderAsString, Book[].class);
        ArrayList<Book> bookArrayList = new ArrayList<>();
        Collections.addAll(bookArrayList,responseBooks);
        Assertions.assertEquals(2,bookArrayList.size());
        Assertions.assertEquals("Ready Player One", bookArrayList.get(0).getTitle());
        Assertions.assertEquals("Dune", bookArrayList.get(1).getTitle());
        Assertions.assertEquals("Nikita", bookArrayList.get(0).getReader().getName());
        Assertions.assertEquals("Nikita", bookArrayList.get(1).getReader().getName());
    }

    private void addBookToReader(Long bookId, Long readerId) throws Exception {
        this.mockMvc.perform(get("/reader/addbook?bookId=" + bookId + "&readerId=" + readerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
