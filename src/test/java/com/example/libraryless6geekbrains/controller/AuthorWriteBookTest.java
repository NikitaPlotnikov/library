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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthorWriteBookTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AddModel addModel;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addBookToAuthorTest() throws Exception {
        Author author = addModel.addAuthor("Ernest Christy Cline");
        Book book = addModel.addBook("Ready Player One");
        Long authorId = author.getId();
        Long bookId = book.getId();
        MvcResult mvcResult = this.mockMvc.perform(get("/author/addbook?bookId=" + bookId + "&authorId=" + authorId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseAuthorAsString = mvcResult.getResponse().getContentAsString();
        Author responseAuthor = objectMapper.readerFor(Author.class).readValue(responseAuthorAsString, Author.class);
        List<Book> bookList = responseAuthor.getWriteBook();
        Assertions.assertNotNull(responseAuthor.getWriteBook());
        Assertions.assertEquals(1, responseAuthor.getWriteBook().size());
        Assertions.assertTrue(bookList.contains(book));
    }
}
