package net.ourdailytech.rest.service;

import net.ourdailytech.rest.exception.ResourceNotFoundException;
import net.ourdailytech.rest.models.Book;
import net.ourdailytech.rest.models.dto.BookDto;
import net.ourdailytech.rest.repositories.BooksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BooksServiceImplIntegrationTestIT {

    @Autowired
    private BooksService booksService;

    @Autowired
    private BooksRepository booksRepository;

    @Test
    void getAllBooks_success() {
        Book book1 = new Book(1L, 2000, "Publisher 1", "Author 1", "Genre 1", 4.5, "Book 1");
        Book book2 = new Book(2L, 2001, "Publisher 2", "Author 2", "Genre 2", 4.0, "Book 2");

        booksRepository.save(book1);
        booksRepository.save(book2);

        List<BookDto> books = booksService.getAllBooks();
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Book 1");
        assertThat(books.get(1).getTitle()).isEqualTo("Book 2");
    }

    @Test
    void updateBook_success() {
        Book existingBook = new Book(1L, 1999, "Old Publisher", "Old Author", "Old Genre", 3.0, "Old Title");
        booksRepository.save(existingBook);

        BookDto updatedDto = new BookDto(1L, 2022, "New Publisher", "New Authors", "New Genre", 5.0, "New Title");

        Optional<BookDto> result = booksService.updateBook(updatedDto);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("New Title");
        assertThat(result.get().getPublisher()).isEqualTo("New Publisher");
    }

    @Test
    void deleteBook_success() {
        Book book = new Book(1L, 2020, "Publisher", "Authors", "Genre", 4.2, "Test Book");
        booksRepository.save(book);

        boolean deleted = booksService.deleteBook(1L);
        assertThat(deleted).isTrue();
        assertThat(booksRepository.existsById(1L)).isFalse();
    }

    @Test
    void deleteBook_notFound() {
        assertThrows(ResourceNotFoundException.class, () -> booksService.deleteBook(1L));
    }
}