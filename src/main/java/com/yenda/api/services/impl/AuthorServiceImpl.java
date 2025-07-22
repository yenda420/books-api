package com.yenda.api.services.impl;

import com.yenda.api.domain.entities.AuthorEntity;
import com.yenda.api.repositories.AuthorRepository;
import com.yenda.api.services.AuthorService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport
                .stream(
                    authorRepository.findAll().spliterator(),
                    false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);

        return authorRepository.findById(id).map(author -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(author::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(author::setAge);

            return authorRepository.save(author);
        }).orElseThrow(() -> new RuntimeException("Author with id " + id + " not found."));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
