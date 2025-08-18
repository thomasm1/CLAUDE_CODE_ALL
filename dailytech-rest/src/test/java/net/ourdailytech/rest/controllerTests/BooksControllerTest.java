package net.ourdailytech.rest.controllerTests;

import net.ourdailytech.rest.controllers.BooksController;
import net.ourdailytech.rest.models.dto.BookDto;
import net.ourdailytech.rest.service.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setPublicationYear(2020);
        bookDto.setPublisher("Test Publisher");
        bookDto.setAuthors("Test Authors");
        bookDto.setGenre("Test Genre");
        bookDto.setRating(4.5);
        bookDto.setTitle("Test Book");
    }

    @Test
    void testGetAllBooks() throws Exception {
        Mockito.when(booksService.getAllBooks()).thenReturn(Arrays.asList(bookDto));

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(bookDto.getId()))
            .andExpect(jsonPath("$[0].title").value(bookDto.getTitle()))
            .andExpect(jsonPath("$[0].authors").value(bookDto.getAuthors()))
            .andExpect(jsonPath("$[0].publisher").value(bookDto.getPublisher()))
            .andExpect(jsonPath("$[0].genre").value(bookDto.getGenre()))
            .andExpect(jsonPath("$[0].rating").value(bookDto.getRating()))
            .andExpect(jsonPath("$[0].publicationYear").value(bookDto.getPublicationYear()));
    }

    @Test
    void testGetBookById() throws Exception {
        Mockito.when(booksService.getBook(1L)).thenReturn(Optional.of(bookDto));

        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(bookDto.getId()))
            .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
            .andExpect(jsonPath("$.authors").value(bookDto.getAuthors()))
            .andExpect(jsonPath("$.publisher").value(bookDto.getPublisher()))
            .andExpect(jsonPath("$.genre").value(bookDto.getGenre()))
            .andExpect(jsonPath("$.rating").value(bookDto.getRating()))
            .andExpect(jsonPath("$.publicationYear").value(bookDto.getPublicationYear()));
    }

    @Test
    void testCreateBook() throws Exception {
        Mockito.when(booksService.createBook(any(BookDto.class))).thenReturn(bookDto);

        String json = "{"
            + "\"title\":\"Test Book\","
            + "\"authors\":\"Test Authors\","
            + "\"publisher\":\"Test Publisher\","
            + "\"genre\":\"Test Genre\","
            + "\"rating\":4.5,"
            + "\"publicationYear\":2020"
            + "}";

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/books/1"))
            .andExpect(jsonPath("$.id").value(bookDto.getId()))
            .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
            .andExpect(jsonPath("$.authors").value(bookDto.getAuthors()))
            .andExpect(jsonPath("$.publisher").value(bookDto.getPublisher()))
            .andExpect(jsonPath("$.genre").value(bookDto.getGenre()))
            .andExpect(jsonPath("$.rating").value(bookDto.getRating()))
            .andExpect(jsonPath("$.publicationYear").value(bookDto.getPublicationYear()));
    }

    @Test
    void testUpdateBook() throws Exception {
        Mockito.when(booksService.updateBook(any(BookDto.class))).thenReturn(Optional.of(bookDto));

        String json = "{"
            + "\"id\":1,"
            + "\"title\":\"Updated Book\","
            + "\"authors\":\"Updated Authors\","
            + "\"publisher\":\"Updated Publisher\","
            + "\"genre\":\"Updated Genre\","
            + "\"rating\":4.8,"
            + "\"publicationYear\":2021"
            + "}";

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(bookDto.getId()))
            .andExpect(jsonPath("$.title").value(bookDto.getTitle()))
            .andExpect(jsonPath("$.authors").value(bookDto.getAuthors()))
            .andExpect(jsonPath("$.publisher").value(bookDto.getPublisher()))
            .andExpect(jsonPath("$.genre").value(bookDto.getGenre()))
            .andExpect(jsonPath("$.rating").value(bookDto.getRating()))
            .andExpect(jsonPath("$.publicationYear").value(bookDto.getPublicationYear()));
    }

    @Test
    void testDeleteBook() throws Exception {
        Mockito.when(booksService.getBook(1L)).thenReturn(Optional.of(bookDto));
        Mockito.when(booksService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}