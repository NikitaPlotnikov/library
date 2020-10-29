package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Reader;
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
class ReaderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addReaderTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/reader")
                .content("{\"id\":null,\"name\":\"Nikita\",\"readBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        Reader reader = objectMapper.readerFor(Reader.class).readValue(responseReaderAsString, Reader.class);
        Assertions.assertNotNull(reader.getId());
        Assertions.assertEquals("Nikita", reader.getName());
    }
    @Test
    void getAllReadersTest() throws Exception {
        addReaders();
        MvcResult mvcResult = this.mockMvc.perform(get("/reader"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        Reader[] readers = objectMapper.readerFor(Reader.class).readValue(responseReaderAsString,Reader[].class);
        ArrayList<Reader> readerArrayList = new ArrayList<>();
        Collections.addAll(readerArrayList,readers);
        Assertions.assertEquals(readerArrayList.size(), 2);
    }

    private void addReaders() throws Exception {
        this.mockMvc.perform(post("/reader")
                .content("{\"id\":null,\"name\":\"Nikita\",\"readBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(post("/reader")
                .content("{\"id\":null,\"name\":\"Nikita\",\"readBook\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
}