package com.task.books;

import com.task.books.model.Book;
import com.task.books.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    // Test for creating a book
    @Test
    public void testCreateBook() {
        // Create a new Book object using setters
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setYear(2022);
        book.setRating(4.0);

        // Send POST request to create the book
        ResponseEntity<Book> response = restTemplate.postForEntity("http://localhost:" + port + "/api/books", book, Book.class);

        // Check if the response status is 200 OK
        assertEquals(200, response.getStatusCodeValue());
        // Check if the created book matches the input data
        assertEquals("Test Book", response.getBody().getTitle());
        assertEquals("Test Author", response.getBody().getAuthor());
        assertEquals(2022, response.getBody().getYear());
        assertEquals(4.0, response.getBody().getRating());
    }

    // Test for updating a book's rating
    @Test
    public void testUpdateBookRating() {
        // Create and save a book in the repository
        Book book = new Book();
        book.setTitle("Another Book");
        book.setAuthor("Some Author");
        book.setYear(2020);
        book.setRating(3.0);
        book = bookRepository.save(book); // Save the book to the database

        // Modify the rating of the book
        book.setRating(5.0);

        // Create an HttpEntity with the updated book data
        HttpEntity<Book> request = new HttpEntity<>(book);

        // Send PUT request to update the book
        ResponseEntity<Book> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/books/" + book.getId(),
                HttpMethod.PUT, request, Book.class);

        // Check if the response status is 200 OK
        assertEquals(200, response.getStatusCodeValue());

        // Check if the rating has been updated
        assertEquals(5.0, response.getBody().getRating());
    }
}
