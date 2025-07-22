package com.yenda.api.utils;

import com.yenda.api.domain.dto.AuthorDto;
import com.yenda.api.domain.dto.BookDto;
import com.yenda.api.domain.entities.AuthorEntity;
import com.yenda.api.domain.entities.BookEntity;

import java.util.List;

public final class TestDataUtil {

    public static AuthorEntity createAuthor() {
        return createAuthorA();
    }

    public static AuthorDto createAuthorDto() {
        return AuthorDto.builder()
                .name("Author DTO")
                .age(55)
                .build();
    }

    private static AuthorEntity createAuthorA() {
        return AuthorEntity.builder()
                .name("Author A")
                .age(80)
                .build();
    }

    private static AuthorEntity createAuthorB() {
        return AuthorEntity.builder()
                .name("Author B")
                .age(30)
                .build();
    }

    private static AuthorEntity createAuthorC() {
        return AuthorEntity.builder()
                .name("Author C")
                .age(20)
                .build();
    }

    public static BookEntity createBook(final AuthorEntity author) {
        return createBookA(author);
    }

    private static BookEntity createBookA(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("ISBN A")
                .title("Title A")
                .author(author)
                .build();
    }

    private static BookEntity createBookB(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("ISBN B")
                .title("Title B")
                .author(author)
                .build();
    }

    private static BookEntity createBookC(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("ISBN C")
                .title("Title C")
                .author(author)
                .build();
    }

    public static BookDto createBookDto(final AuthorEntity author) {
        return BookDto.builder()
                .isbn("ISBN DTO")
                .title("Title DTO")
                .author(author)
                .build();
    }

    public static List<AuthorEntity> getAuthors() {
        return List.of(
            createAuthorA(),
            createAuthorB(),
            createAuthorC()
        );
    }

    public static List<BookEntity> getBooks() {
        return List.of(
            createBookA(createAuthorA()),
            createBookB(createAuthorB()),
            createBookC(createAuthorC())
        );
    }
}
