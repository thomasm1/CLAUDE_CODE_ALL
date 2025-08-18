// src/test/java/net/ourdailytech/rest/controllerTests/BooksControllerIntegrationTestIT.java
package net.ourdailytech.rest.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ourdailytech.rest.models.dto.BookDto;
import net.ourdailytech.rest.service.BooksService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerIntegrationTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BooksService booksService;

    @Test
    void testGetAllBooks() throws Exception {
        Mockito.when(booksService.getAllBooks()).thenReturn(Arrays.asList(
            new BookDto(1L,2000,"Book 1", "Author 1", "ISBN1",12.1,"Book 1"),
            new BookDto(2L,2000, "Book 2", "Author 2", "ISBN2",12.1,"Book 2")
        ));

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].title").value("Book 1"))
            .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void testGetBookById() throws Exception {
        Mockito.when(booksService.getBook(1L)).thenReturn(Optional.of(new BookDto(1L, 2000,"Book 1", "Author 1", "ISBN1",12.1, "Book 1")));

        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Book 1"))
            .andExpect(jsonPath("$.author").value("Author 1"));
    }

    @Test
    void testCreateBook() throws Exception {
        BookDto bookDto = new BookDto(1L,2000,"Book 1", "Author 1", "ISBN1",12.1,"Book 1");
        BookDto savedBook = new BookDto(1L,2000,"Book 1", "Author 1", "ISBN1",12.1,"Book 1");

        Mockito.when(booksService.createBook(any(BookDto.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/books/1"))
            .andExpect(jsonPath("$.title").value("Book 1"));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDto bookDto = new BookDto(1L,2002, "Updated Book", "Updated Author", "ISBN123",67.8,"Book 1");

        Mockito.when(booksService.updateBook(any(BookDto.class))).thenReturn(Optional.of(bookDto));

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void testDeleteBook() throws Exception {
        Mockito.when(booksService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}