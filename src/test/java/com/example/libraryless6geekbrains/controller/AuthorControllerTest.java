package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Author;
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
class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addAuthorTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/author")
                .content("{\"id\":null,\"name\":\"Remark\",\"writeBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseAuthorAsString = mvcResult.getResponse().getContentAsString();
        Author author = objectMapper.readerFor(Author.class).readValue(responseAuthorAsString, Author.class);
        Assertions.assertNotNull(author.getId());
        Assertions.assertEquals("Remark", author.getName());
    }

    @Test
    void getAllAuthorsTest() throws Exception {
        addAuthors();
        MvcResult mvcResult = this.mockMvc.perform(get("/author"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseAuthorAsString = mvcResult.getResponse().getContentAsString();
        Author[] authors = objectMapper.readerFor(Author.class).readValue(responseAuthorAsString,Author[].class);
        ArrayList<Author> authorArrayList = new ArrayList<>();
        Collections.addAll(authorArrayList,authors);
        Assertions.assertEquals(authorArrayList.size(), 2);
    }


    private void addAuthors() throws Exception {
        this.mockMvc.perform(post("/author")
                .content("{\"id\":null,\"name\":\"Tolkien\",\"writeBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(post("/author")
                .content("{\"id\":null,\"name\":\"Remark\",\"writeBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

    }
}