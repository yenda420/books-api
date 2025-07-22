package com.yenda.api.repositories;

import com.yenda.api.domain.entities.AuthorEntity;
import com.yenda.api.utils.TestDataUtil;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryTests(final AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testPresence() {
        final AuthorEntity author = TestDataUtil.createAuthor();
        underTest.save(author);

        final Optional<AuthorEntity> resultAuthor = underTest.findById(author.getId());
        assertThat(resultAuthor).isPresent();
        assertThat(resultAuthor.get()).isEqualTo(author);
    }

    @Test
    public void testPresenceMultiple() {
        final List<AuthorEntity> authorList = TestDataUtil.getAuthors();

        underTest.saveAll(authorList);

        final Iterable<AuthorEntity> authors = underTest.findAll();

        for (AuthorEntity author : authorList) {
            assertThat(authors).contains(author);
        }
    }

    @Test
    public void testUpdate() {
        final AuthorEntity author = TestDataUtil.createAuthor();

        underTest.save(author);
        author.setName("UPDATED");

        underTest.save(author);

        final Optional<AuthorEntity> resultAuthor = underTest.findById(author.getId());
        assertThat(resultAuthor).isPresent();
        assertThat(resultAuthor.get()).isEqualTo(author);
    }

    @Test
    public void testDelete() {
        final AuthorEntity author = TestDataUtil.createAuthor();

        underTest.save(author);
        underTest.delete(author);

        Optional<AuthorEntity> resultAuthor = underTest.findById(author.getId());
        assertThat(resultAuthor).isEmpty();
    }

    @Test
    public void testGetAuthorsWithAgeLessThan() {
        List<AuthorEntity> authorList = TestDataUtil.getAuthors();
        underTest.saveAll(authorList);

        Iterable<AuthorEntity> authorsResult = underTest.ageLessThan(50);

        assertThat(authorsResult).containsExactly(authorList.get(1), authorList.get(2));
    }
}
