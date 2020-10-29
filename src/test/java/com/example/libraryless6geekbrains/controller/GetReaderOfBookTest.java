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
public class GetReaderOfBookTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddModel addModel;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getReaderOfBookTest() throws Exception {
        Book bookShantaram = addModel.addBook("Shantaram");
        Reader readerOleg = addModel.addReader("Oleg");
        Long shantaramId = bookShantaram.getId();
        Long olegId = readerOleg.getId();
        readerReadBook(shantaramId,olegId);
        MvcResult mvcResult = this.mockMvc.perform(get("/book/reader?id=" + shantaramId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        Reader reader = objectMapper.readerFor(Reader.class).readValue(responseReaderAsString, Reader.class);
        Assertions.assertNotNull(reader.getId());
        Assertions.assertEquals( "Oleg",reader.getName());
    }
    private void readerReadBook(Long bookId,Long readerId) throws Exception {
        this.mockMvc.perform(get("/reader/addbook?bookId=" + bookId + "&readerId=" + readerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
