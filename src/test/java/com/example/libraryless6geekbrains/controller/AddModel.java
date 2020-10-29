package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Author;
import com.example.libraryless6geekbrains.model.Book;
import com.example.libraryless6geekbrains.model.Reader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Service
public class AddModel {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public Book addBook(String title) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/book")
                .content("{\"id\":null,\"title\":\"" + title + "\",\"author\":null,\"reader\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseBookAsString = mvcResult.getResponse().getContentAsString();
        return objectMapper.readerFor(Book.class).readValue(responseBookAsString, Book.class);
    }
    public Author addAuthor(String name) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/author")
                .content("{\"id\":null,\"name\":\"" + name + "\",\"writeBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseBookAsString = mvcResult.getResponse().getContentAsString();
        return objectMapper.readerFor(Author.class).readValue(responseBookAsString, Author.class);
    }
    public Reader addReader(String name) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/reader")
                .content("{\"id\":null,\"name\":\"" + name + "\",\"readBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        return objectMapper.readerFor(Reader.class).readValue(responseReaderAsString, Reader.class);
    }
}

