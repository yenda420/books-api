package com.yenda.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yenda.api.domain.dto.BookDto;
import com.yenda.api.domain.entities.BookEntity;
import com.yenda.api.services.BookService;
import com.yenda.api.utils.TestDataUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @Autowired
    public BookControllerTests(final MockMvc mockMvc, final BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreate() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" +  bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testCreateReturnsCreated() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" +  bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testGetAllStatus() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testGetAllBody() throws Exception {
        BookEntity book = TestDataUtil.createBook(null);
        bookService.createUpdate(book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value(book.getTitle())
        );
    }

    @Test
    public void testGetOneStatus201() throws Exception {
        BookEntity book = TestDataUtil.createBook(null);
        bookService.createUpdate(book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testGetOneStatus404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/999")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testUpdateStatus201() throws Exception {
        BookEntity book = TestDataUtil.createBook(null);
        BookDto bookDto = TestDataUtil.createBookDto(null);

        bookService.createUpdate(book);

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" +  bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testUpdate() throws Exception {
        BookEntity book = TestDataUtil.createBook(null);
        BookEntity savedBook = bookService.createUpdate(book);

        BookDto bookDto = TestDataUtil.createBookDto(null);
        bookDto.setIsbn(savedBook.getIsbn());
        bookDto.setTitle("UPDATED");

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" +  savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testPartialUpdateStatus200() throws Exception {
        BookEntity book = TestDataUtil.createBook(null);
        bookService.createUpdate(book);

        BookDto bookDto = TestDataUtil.createBookDto(null);
        bookDto.setTitle("UPDATED");

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testPartialUpdateBody() throws Exception {
        BookEntity book = TestDataUtil.createBook(null);
        bookService.createUpdate(book);

        BookDto bookDto = TestDataUtil.createBookDto(null);
        String updatedTitle = "UPDATED";

        bookDto.setTitle(updatedTitle);

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(updatedTitle)
        );
    }

    @Test
    public void testDeleteStatus204NonExistingBook() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testDeleteStatus204ExistingBook() throws Exception {
        BookEntity book = TestDataUtil.createBook(null);
        bookService.createUpdate(book);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
