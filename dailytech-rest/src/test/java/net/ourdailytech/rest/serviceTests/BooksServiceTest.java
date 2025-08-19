package net.ourdailytech.rest.serviceTests;

import net.ourdailytech.rest.exception.ResourceNotFoundException;
import net.ourdailytech.rest.models.Book;
import net.ourdailytech.rest.models.dto.BookDto;
import net.ourdailytech.rest.repositories.BooksRepository;
import net.ourdailytech.rest.service.BooksServiceImpl;
import net.ourdailytech.rest.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BooksServiceTest {

    @Mock
    private BooksRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BooksServiceImpl booksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        BookDto bookDto = new BookDto(1L, 2020, "Publisher", "Author", "Genre", 4.5, "Test Book");
        Book book = new Book(1L, 2020, "Publisher", "Author", "Genre", 4.5, "Test Book");

        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto createdBook = booksService.createBook(bookDto);

        assertNotNull(createdBook);
        assertEquals("Test Book", createdBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetBookById() {
        Book book = new Book(1L, 2020, "Publisher", "Author", "Genre", 4.5, "Test Book");
        BookDto bookDto = new BookDto(1L, 2020, "Publisher", "Author", "Genre", 4.5, "Test Book");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = booksService.getBook(1L).get();

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> booksService.getBook(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book(1L, 2020, "Publisher1", "Author1", "Genre1", 4.5, "Book 1");
        Book book2 = new Book(2L, 2021, "Publisher2", "Author2", "Genre2", 4.0, "Book 2");
        BookDto bookDto1 = new BookDto(1L, 2020, "Publisher1", "Author1", "Genre1", 4.5, "Book 1");
        BookDto bookDto2 = new BookDto(2L, 2021, "Publisher2", "Author2", "Genre2", 4.0, "Book 2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
        when(bookMapper.toDto(book1)).thenReturn(bookDto1);
        when(bookMapper.toDto(book2)).thenReturn(bookDto2);

        List<BookDto> bookDtos = booksService.getAllBooks();

        assertEquals(2, bookDtos.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(1L, 2020, "Publisher", "Author", "Genre", 4.5, "Old Title");
        BookDto bookDto = new BookDto(1L, 2021, "Publisher", "Author", "Genre", 4.8, "New Title");
        Book updatedBook = new Book(1L, 2021, "Publisher", "Author", "Genre", 4.8, "New Title");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.partialUpdate(bookDto, book)).thenReturn(updatedBook);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(bookDto);

        Optional<BookDto> result = booksService.updateBook(bookDto);
        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
    }
}