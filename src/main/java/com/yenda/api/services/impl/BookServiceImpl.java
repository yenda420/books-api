package com.yenda.api.services.impl;

import com.yenda.api.domain.entities.BookEntity;
import com.yenda.api.repositories.BookRepository;
import com.yenda.api.services.BookService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookEntity createUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity createUpdate(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport
                .stream(
                    bookRepository.findAll().spliterator(),
                    false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean exists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);

        return bookRepository.findById(isbn).map(book -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(book::setTitle);
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book with id " + isbn + " not found."));
     }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
