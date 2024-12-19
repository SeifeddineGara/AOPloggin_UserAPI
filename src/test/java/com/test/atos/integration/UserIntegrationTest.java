package com.test.atos.integration;

import com.test.atos.dto.UserRequest;
import com.test.atos.dto.UserResponse;
import com.test.atos.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for User API endpoints.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    /**
     * Sets up common HTTP headers before each test.
     */
    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Test successful user registration via API.
     */
    @Test
    void registerUser_Success() {
        // Create a sample UserRequest
        UserRequest request = UserRequest.builder()
                .username("alice")
                .birthdate(LocalDate.of(1992, 8, 20))
                .countryOfResidence("France")
                .phoneNumber("+33111222333")
                .gender(User.Gender.FEMALE)
                .build();

        // Create HTTP entity with headers and body
        HttpEntity<UserRequest> entity = new HttpEntity<>(request, headers);

        // Perform POST request to register user
        ResponseEntity<UserResponse> response = restTemplate.postForEntity("/api/users/register", entity, UserResponse.class);

        // Assertions to verify successful registration
         assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("alice", response.getBody().getUsername());
        assertEquals("France", response.getBody().getCountryOfResidence());
    }

    /**
     * Test user registration with underage user via API.
     */
    @Test
    void registerUser_Underage() {
        // Create a sample UserRequest with age less than 18
        UserRequest request = UserRequest.builder()
                .username("young_user")
                .birthdate(LocalDate.now().minusYears(17))
                .countryOfResidence("France")
                .build();

        // Create HTTP entity with headers and body
        HttpEntity<UserRequest> entity = new HttpEntity<>(request, headers);

        // Perform POST request to register user
        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register", entity, String.class);

        // Assertions to verify validation error
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("User must be at least 18 years old to register"));
    }

    /**
     * Test user registration with non-French resident via API.
     */
    @Test
    void registerUser_NonFrenchResident() {
        // Create a sample UserRequest with non-French country
        UserRequest request = UserRequest.builder()
                .username("foreign_user")
                .birthdate(LocalDate.of(1990, 1, 1))
                .countryOfResidence("USA")
                .build();

        // Create HTTP entity with headers and body
        HttpEntity<UserRequest> entity = new HttpEntity<>(request, headers);

        // Perform POST request to register user
        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register", entity, String.class);

        // Assertions to verify validation error
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Only French residents can register"));
    }

    /**
     * Test retrieval of existing user details via API.
     */
    @Test
    void getUserDetails_Success() {
        // First, register a new user
        UserRequest request = UserRequest.builder()
                .username("bob")
                .birthdate(LocalDate.of(1985, 3, 10))
                .countryOfResidence("France")
                .phoneNumber("+33987654321")
                .gender(User.Gender.MALE)
                .build();

        HttpEntity<UserRequest> registerEntity = new HttpEntity<>(request, headers);
        ResponseEntity<UserResponse> registerResponse = restTemplate.postForEntity("/api/users/register", registerEntity, UserResponse.class);

        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        Long userId = registerResponse.getBody().getId();

        // Perform GET request to retrieve user details
        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity("/api/users/" + userId, UserResponse.class);

        // Assertions to verify successful retrieval
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("bob", getResponse.getBody().getUsername());
        assertEquals("France", getResponse.getBody().getCountryOfResidence());
    }

    /**
     * Test retrieval of non-existing user details via API.
     */
    @Test
    void getUserDetails_NotFound() {
        // Perform GET request with a non-existing user ID
        ResponseEntity<String> response = restTemplate.getForEntity("/api/users/99999", String.class);

        // Assertions to verify not found error
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("User not found with id: 99999"));
    }
}
