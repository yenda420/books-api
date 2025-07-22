package com.yenda.api.repositories;

import com.yenda.api.domain.entities.AuthorEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("SELECT a FROM AuthorEntity a WHERE a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}
