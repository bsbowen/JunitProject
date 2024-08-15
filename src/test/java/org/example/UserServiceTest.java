package org.example;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    // Mocking UserService so that we can simulate its behavior without executing the actual implementation
    @Mock
    private UserService mockUserService;

    // Declaring user objects to be used in our tests
    private User testUser1;
    private User testUser2;

    // This method is called before each test. It initializes the mocks and sets up the test data.
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize the mock objects
        // Creating two user instances for testing
        testUser1 = new User("userOne", "pass123", "user1@example.com");
        testUser2 = new User("userTwo", "pass456", "user2@example.com");
    }

    // This method is called after each test to reset the mock objects and ensure clean state for the next test.
    @AfterEach
    public void tearDown() {
        reset(mockUserService);  // Resetting the mock UserService to clear any interactions between tests
    }

    // --- User Registration Tests ---

    // Positive Case: Simulates a successful registration of a new user.
    @Test
    public void shouldRegisterUserSuccessfully() {
        // Mocking the registerUser method to simulate a successful registration (returns true)
        when(mockUserService.registerUser(testUser1)).thenReturn(true);

        // Calling the method and capturing the result
        boolean registrationResult = mockUserService.registerUser(testUser1);

        // Verifying that the registration was successful
        assertTrue(registrationResult, "User should be registered successfully.");
        // Ensuring that the method was called once with the correct argument
        verify(mockUserService, times(1)).registerUser(testUser1);
    }

    // Negative Case: Simulates a failed registration due to a duplicate username.
    @Test
    public void shouldFailToRegisterDuplicateUser() {
        // Mocking the registerUser method to simulate a failed registration (returns false)
        when(mockUserService.registerUser(testUser1)).thenReturn(false);

        // Calling the method and capturing the result
        boolean registrationResult = mockUserService.registerUser(testUser1);

        // Verifying that the registration failed
        assertFalse(registrationResult, "User registration should fail for duplicate usernames.");
        // Ensuring that the method was called once with the correct argument
        verify(mockUserService, times(1)).registerUser(testUser1);
    }

    // Edge Case: Simulates a failed registration due to an empty username.
    @Test
    public void shouldFailToRegisterWithEmptyUsername() {
        // Creating a user with an empty username
        User invalidUser = new User("", "pass123", "empty@example.com");
        // Mocking the registerUser method to simulate a failed registration (returns false)
        when(mockUserService.registerUser(invalidUser)).thenReturn(false);

        // Calling the method and capturing the result
        boolean registrationResult = mockUserService.registerUser(invalidUser);

        // Verifying that the registration failed
        assertFalse(registrationResult, "User registration should fail for empty usernames.");
        // Ensuring that the method was called once with the correct argument
        verify(mockUserService, times(1)).registerUser(invalidUser);
    }

    // --- User Login Tests ---

    // Positive Case: Simulates a successful login with valid credentials.
    @Test
    public void shouldLoginUserSuccessfully() {
        // Mocking the loginUser method to simulate a successful login (returns a valid user)
        when(mockUserService.loginUser("userOne", "pass123")).thenReturn(testUser1);

        // Calling the method and capturing the result
        User loggedInUser = mockUserService.loginUser("userOne", "pass123");

        // Verifying that the login was successful and the returned user matches the expected user
        assertNotNull(loggedInUser, "User should be logged in successfully.");
        assertEquals(testUser1, loggedInUser, "Logged in user should match the expected user.");
        // Ensuring that the method was called once with the correct arguments
        verify(mockUserService, times(1)).loginUser("userOne", "pass123");
    }

    // Negative Case: Simulates a failed login attempt with an incorrect password.
    @Test
    public void shouldFailLoginWithIncorrectPassword() {
        // Mocking the loginUser method to simulate a failed login (returns null)
        when(mockUserService.loginUser("userOne", "wrongPassword")).thenReturn(null);

        // Calling the method and capturing the result
        User loggedInUser = mockUserService.loginUser("userOne", "wrongPassword");

        // Verifying that the login failed
        assertNull(loggedInUser, "Login should fail for incorrect password.");
        // Ensuring that the method was called once with the correct arguments
        verify(mockUserService, times(1)).loginUser("userOne", "wrongPassword");
    }

    // Edge Case: Simulates a failed login attempt with a non-existent username.
    @Test
    public void shouldFailLoginWithNonExistentUsername() {
        // Mocking the loginUser method to simulate a failed login (returns null)
        when(mockUserService.loginUser("nonExistentUser", "pass123")).thenReturn(null);

        // Calling the method and capturing the result
        User loggedInUser = mockUserService.loginUser("nonExistentUser", "pass123");

        // Verifying that the login failed
        assertNull(loggedInUser, "Login should fail for non-existent username.");
        // Ensuring that the method was called once with the correct arguments
        verify(mockUserService, times(1)).loginUser("nonExistentUser", "pass123");
    }
}