package com.yenda.api.repositories;

import com.yenda.api.domain.entities.AuthorEntity;
import com.yenda.api.domain.entities.BookEntity;
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
public class BookRepositoryTests {

    private final BookRepository underTest;

    @Autowired
    public BookRepositoryTests(BookRepository bookRepository) {
        this.underTest = bookRepository;
    }

    @Test
    public void testPresence() {
        BookEntity book = TestDataUtil.createBook(TestDataUtil.createAuthor());

        underTest.save(book);

        Optional<BookEntity> resultBook = underTest.findById(book.getIsbn());

        assertThat(resultBook).isPresent();
        book.getAuthor().setId(resultBook.get().getAuthor().getId());

        assertThat(resultBook.get()).isEqualTo(book);
    }

    @Test
    public void testPresenceMultiple() {
        List<BookEntity> bookList = TestDataUtil.getBooks();

        underTest.saveAll(bookList);

        Iterable<BookEntity> books = underTest.findAll();
        int i = 0;

        for (BookEntity book : books) {
            AuthorEntity expectedAuthor = book.getAuthor();
            if (expectedAuthor == null) {
                continue;
            }

            bookList.get(i).getAuthor().setId(expectedAuthor.getId());
            i++;
        }

        for (BookEntity book : bookList) {
            assertThat(books).contains(book);
        }
    }

    @Test
    public void testUpdate() {
        BookEntity book = TestDataUtil.createBook(TestDataUtil.createAuthor());
        underTest.save(book);

        book.setTitle("UPDATED");
        underTest.save(book);

        Optional<BookEntity> resultBook = underTest.findById(book.getIsbn());

        assertThat(resultBook).isPresent();
        book.getAuthor().setId(resultBook.get().getAuthor().getId());

        assertThat(resultBook.get()).isEqualTo(book);
    }

    @Test
    public void testDelete() {
        BookEntity book = TestDataUtil.createBook(TestDataUtil.createAuthor());

        underTest.save(book);
        underTest.delete(book);

        Optional<BookEntity> resultBook = underTest.findById(book.getIsbn());
        assertThat(resultBook).isEmpty();
    }
}
