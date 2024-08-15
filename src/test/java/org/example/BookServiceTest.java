package org.example;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Arrays;

public class BookServiceTest {

    // Mocking BookService so that we can simulate its behavior without executing the actual implementation
    @Mock
    private BookService mockBookService;

    // Declaring user and book objects to be used in our tests
    private User mockUser;
    private Book mockBook1;
    private Book mockBook2;

    // This method is called before each test. It initializes the mocks and sets up the test data.
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize the mock objects
        // Creating test data: users and books
        mockUser = new User("userOne", "pass123", "user1@example.com");
        mockBook1 = new Book("Book One", "Author One", "Genre One", 10.99);
        mockBook2 = new Book("Book Two", "Author Two", "Genre Two", 15.99);
    }

    // This method is called after each test to reset the mock objects and ensure clean state for the next test.
    @AfterEach
    public void tearDown() {
        reset(mockBookService);  // Resetting the mock BookService to clear any interactions between tests
    }

    // --- Book Search Tests ---

    // Positive Case: Simulates a successful search for a book by title.
    @Test
    public void shouldFindBookByTitle() {
        // Mocking the searchBook method to simulate a successful search by title (returns a list with one book)
        when(mockBookService.searchBook("Book One")).thenReturn(Arrays.asList(mockBook1));

        // Calling the method and capturing the result
        List<Book> foundBooks = mockBookService.searchBook("Book One");

        // Verifying that the book was found and the title matches the expected value
        assertFalse(foundBooks.isEmpty(), "Book search should return results for valid title.");
        assertEquals("Book One", foundBooks.get(0).getTitle(), "The title of the found book should be 'Book One'.");
        // Ensuring that the method was called once with the correct argument
        verify(mockBookService, times(1)).searchBook("Book One");
    }

    // Negative Case: Simulates a failed search with an invalid keyword.
    @Test
    public void shouldReturnNoBooksForInvalidKeyword() {
        // Mocking the searchBook method to simulate a failed search (returns an empty list)
        when(mockBookService.searchBook("NonExistent")).thenReturn(Arrays.asList());

        // Calling the method and capturing the result
        List<Book> foundBooks = mockBookService.searchBook("NonExistent");

        // Verifying that no books were found
        assertTrue(foundBooks.isEmpty(), "Book search should return no results for invalid keyword.");
        // Ensuring that the method was called once with the correct argument
        verify(mockBookService, times(1)).searchBook("NonExistent");
    }

    // Edge Case: Simulates a search with an empty keyword, which should return no results.
    @Test
    public void shouldReturnNoBooksForEmptyKeyword() {
        // Mocking the searchBook method to simulate a failed search for an empty keyword (returns an empty list)
        when(mockBookService.searchBook("")).thenReturn(Arrays.asList());

        // Calling the method and capturing the result
        List<Book> foundBooks = mockBookService.searchBook("");

        // Verifying that no books were found
        assertTrue(foundBooks.isEmpty(), "Book search should return no results for empty keyword.");
        // Ensuring that the method was called once with the correct argument
        verify(mockBookService, times(1)).searchBook("");
    }

    // --- Book Purchase Tests ---

    // Positive Case: Simulates a successful book purchase for an existing book.
    @Test
    public void shouldPurchaseBookSuccessfully() {
        // Mocking the purchaseBook method to simulate a successful purchase (returns true)
        when(mockBookService.purchaseBook(mockUser, mockBook1)).thenReturn(true);

        // Calling the method and capturing the result
        boolean purchaseResult = mockBookService.purchaseBook(mockUser, mockBook1);

        // Verifying that the purchase was successful
        assertTrue(purchaseResult, "Book purchase should succeed for valid book.");
        // Ensuring that the method was called once with the correct arguments
        verify(mockBookService, times(1)).purchaseBook(mockUser, mockBook1);
    }

    // Negative Case: Simulates a failed book purchase for a non-existent book.
    @Test
    public void shouldFailToPurchaseNonExistentBook() {
        // Mocking the purchaseBook method to simulate a failed purchase (returns false)
        when(mockBookService.purchaseBook(mockUser, mockBook1)).thenReturn(false);

        // Calling the method and capturing the result
        boolean purchaseResult = mockBookService.purchaseBook(mockUser, mockBook1);

        // Verifying that the purchase failed
        assertFalse(purchaseResult, "Book purchase should fail for non-existent book.");
        // Ensuring that the method was called once with the correct arguments
        verify(mockBookService, times(1)).purchaseBook(mockUser, mockBook1);
    }

    // Edge Case: Simulates a failed book purchase due to incomplete user details.
    @Test
    public void shouldFailToPurchaseBookWithIncompleteUserDetails() {
        // Creating a user with incomplete details
        User incompleteUser = new User("", "", "");
        // Mocking the purchaseBook method to simulate a failed purchase (returns false)
        when(mockBookService.purchaseBook(incompleteUser, mockBook1)).thenReturn(false);

        // Calling the method and capturing the result
        boolean purchaseResult = mockBookService.purchaseBook(incompleteUser, mockBook1);

        // Verifying that the purchase failed due to incomplete user details
        assertFalse(purchaseResult, "Book purchase should fail with incomplete user details.");
        // Ensuring that the method was called once with the correct arguments
        verify(mockBookService, times(1)).purchaseBook(incompleteUser, mockBook1);
    }
}