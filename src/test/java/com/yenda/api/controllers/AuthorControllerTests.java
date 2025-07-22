package com.yenda.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yenda.api.domain.dto.AuthorDto;
import com.yenda.api.domain.entities.AuthorEntity;
import com.yenda.api.services.AuthorService;
import com.yenda.api.utils.TestDataUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorControllerTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthorService authorService;

    @Autowired
    public AuthorControllerTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreate() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        author.setId(null);

        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testCreateReturnsCreated() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        author.setId(null);

        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void testGetAllStatus() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testGetAllBody() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        AuthorEntity savedAuthor = authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(savedAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].age").value(savedAuthor.getAge())
        );
    }

    @Test
    public void testGetOneStatus201() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testGetOneStatus404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testOneBody() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        authorService.save(author);

        System.out.println(author.getId());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" +  author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void testUpdateStatus404() throws Exception {
        AuthorDto author = TestDataUtil.createAuthorDto();
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testUpdateStatus200() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        AuthorEntity savedAuthor = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" +  savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testUpdate() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        AuthorEntity savedAuthor = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        authorDto.setId(savedAuthor.getId());

        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" +  savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testPartialUpdateStatus200() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        AuthorEntity savedAuthor = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        authorDto.setName("UPDATED NAME");
        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" +  savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testPartialUpdateBody() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        AuthorEntity savedAuthor = authorService.save(author);
        AuthorDto authorDto = TestDataUtil.createAuthorDto();
        String updatedName = "UPDATED NAME";

        authorDto.setName(updatedName);

        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" +  savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(updatedName)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testDeleteStatus204NonExistingAuthor() throws Exception {
        mockMvc.perform(
                    MockMvcRequestBuilders.delete("/authors/999")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testDeleteStatus204ExistingAuthor() throws Exception {
        AuthorEntity author = TestDataUtil.createAuthor();
        AuthorEntity savedAuthor = authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" +  savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
