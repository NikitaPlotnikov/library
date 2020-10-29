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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReaderReadBookTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddModel addModel;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addBookToReader() throws Exception {
        Reader reader = addModel.addReader("Nikita");
        Book book = addModel.addBook("Ready Player One");
        Long readerId = reader.getId();
        Long bookId = book.getId();
        MvcResult mvcResult = this.mockMvc.perform(get("/reader/addbook?bookId=" + bookId + "&readerId=" + readerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        Reader responseReader = objectMapper.readerFor(Reader.class).readValue(responseReaderAsString, Reader.class);
        Book book1 = responseReader.getReadBook().get(0);
        Assertions.assertNotNull(responseReader.getReadBook());
        Assertions.assertEquals(1, responseReader.getReadBook().size());
        Assertions.assertEquals(book.getTitle(),book1.getTitle());
    }
}