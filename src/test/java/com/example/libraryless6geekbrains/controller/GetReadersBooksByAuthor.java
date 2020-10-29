package com.example.libraryless6geekbrains.controller;

import com.example.libraryless6geekbrains.model.Author;
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
public class GetReadersBooksByAuthor {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddModel addModel;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getReadersBooksByAuthor() throws Exception {
        Author georgeOrwell = addModel.addAuthor("George Orwell");
        Reader nikita = addModel.addReader("Nikita");
        Reader oleg = addModel.addReader("Oleg");
        Book book1984 = addModel.addBook("1984");
        Book animalFarm = addModel.addBook("Animal Farm");
        Book comingUpForAir = addModel.addBook("Coming Up for Air");

        Long georgeOrwellId = georgeOrwell.getId();
        Long nikitaId = nikita.getId();
        Long olegId = oleg.getId();
        Long book1984Id = book1984.getId();
        Long animalFarmId = animalFarm.getId();
        Long comingUpForAirId = comingUpForAir.getId();

        authorWriteBook(book1984Id,georgeOrwellId);
        authorWriteBook(animalFarmId,georgeOrwellId);
        authorWriteBook(comingUpForAirId,georgeOrwellId);

        readerReadBook(book1984Id,nikitaId);
        readerReadBook(animalFarmId,nikitaId);
        readerReadBook(comingUpForAirId,olegId);

        MvcResult mvcResult = this.mockMvc.perform(get("/author/books/readers?id=" + georgeOrwellId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseReaderAsString = mvcResult.getResponse().getContentAsString();
        Reader[] responseBooks = objectMapper.readerFor(Reader.class).readValue(responseReaderAsString, Reader[].class);
        ArrayList<Reader> readerArrayList = new ArrayList<>();
        Collections.addAll(readerArrayList,responseBooks);

        Assertions.assertEquals(2,readerArrayList.size());
        Assertions.assertEquals("Nikita", readerArrayList.get(0).getName());
        Assertions.assertEquals("Oleg", readerArrayList.get(1).getName());
    }
    private void authorWriteBook(Long bookId, Long authorId) throws Exception {
        this.mockMvc.perform(get("/author/addbook?bookId=" + bookId + "&authorId=" + authorId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
    private void readerReadBook(Long bookId, Long readerId) throws Exception {
        this.mockMvc.perform(get("/reader/addbook?bookId=" + bookId + "&readerId=" + readerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}