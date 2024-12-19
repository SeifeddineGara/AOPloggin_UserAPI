package com.test.atos.unit;

import com.test.atos.dto.UserRequest;
import com.test.atos.dto.UserResponse;
import com.test.atos.entity.User;
import com.test.atos.exception.ResourceNotFoundException;
import com.test.atos.exception.ValidationException;
import com.test.atos.mapper.UserMapper;
import com.test.atos.repository.UserRepository;
import com.test.atos.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the UserService class.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    /**
     * Test successful user registration.
     */
    @Test
    void registerUser_Success() {
        // Create a sample UserRequest
        UserRequest request = UserRequest.builder()
                .username("jane_doe")
                .birthdate(LocalDate.of(1995, 1, 1))
                .countryOfResidence("France")
                .phoneNumber("+33123456789")
                .gender(User.Gender.FEMALE)
                .build();

        // Create a corresponding User entity
        User userEntity = User.builder()
                .username("jane_doe")
                .birthdate(request.getBirthdate())
                .countryOfResidence("France")
                .phoneNumber("+33123456789")
                .gender(User.Gender.FEMALE)
                .build();

        // Create a saved User entity with an ID
        User savedUser = User.builder()
                .id(1L)
                .username("jane_doe")
                .birthdate(request.getBirthdate())
                .countryOfResidence("France")
                .phoneNumber("+33123456789")
                .gender(User.Gender.FEMALE)
                .build();

        // Create a corresponding UserResponse DTO
        UserResponse response = UserResponse.builder()
                .id(1L)
                .username("jane_doe")
                .birthdate(request.getBirthdate())
                .countryOfResidence("France")
                .phoneNumber("+33123456789")
                .gender(User.Gender.FEMALE)
                .build();

        // Mock repository and mapper behaviors
        when(userRepository.findByUsername("jane_doe")).thenReturn(Optional.empty());
        when(userMapper.toEntity(request)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(response);

        // Invoke the service method
        UserResponse result = userService.registerUser(request);

        // Assertions to verify expected behavior
        assertNotNull(result);
        assertEquals("jane_doe", result.getUsername());
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findByUsername("jane_doe");
        verify(userRepository, times(1)).save(userEntity);
    }

    /**
     * Test user registration when the username already exists.
     */
    @Test
    void registerUser_UserAlreadyExists() {
        // Create a sample UserRequest
        UserRequest request = UserRequest.builder()
                .username("jane_doe")
                .birthdate(LocalDate.of(1995, 1, 1))
                .countryOfResidence("France")
                .build();

        // Mock repository to return an existing user
        when(userRepository.findByUsername("jane_doe")).thenReturn(Optional.of(new User()));

        // Expect a ValidationException to be thrown
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(request);
        });

        // Verify the exception message
        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("jane_doe");
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test user registration with an invalid country.
     */
    @Test
    void registerUser_InvalidCountry() {
        // Create a sample UserRequest with a non-French country
        UserRequest request = UserRequest.builder()
                .username("john_doe")
                .birthdate(LocalDate.of(1990, 1, 1))
                .countryOfResidence("USA")
                .build();

        // Expect a ValidationException to be thrown
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(request);
        });

        // Verify the exception message
        assertEquals("Only French residents can register", exception.getMessage());
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test user registration when the user is underage.
     */
    @Test
    void registerUser_Underage() {
        // Create a sample UserRequest with age less than 18
        LocalDate today = LocalDate.now();
        UserRequest request = UserRequest.builder()
                .username("young_user")
                .birthdate(today.minusYears(17))
                .countryOfResidence("France")
                .build();

        // Expect a ValidationException to be thrown
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(request);
        });

        // Verify the exception message
        assertEquals("User must be at least 18 years old to register", exception.getMessage());
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test successful retrieval of user details by ID.
     */
    @Test
    void getUserById_Success() {
        // Create a sample User entity
        User user = User.builder()
                .id(1L)
                .username("john_doe")
                .birthdate(LocalDate.of(1990, 5, 15))
                .countryOfResidence("France")
                .phoneNumber("+33123456789")
                .gender(User.Gender.MALE)
                .build();

        // Create a corresponding UserResponse DTO
        UserResponse response = UserResponse.builder()
                .id(1L)
                .username("john_doe")
                .birthdate(user.getBirthdate())
                .countryOfResidence("France")
                .phoneNumber("+33123456789")
                .gender(User.Gender.MALE)
                .build();

        // Mock repository and mapper behaviors
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(response);

        // Invoke the service method
        UserResponse result = userService.getUserById(1L);

        // Assertions to verify expected behavior
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Test retrieval of user details when the user does not exist.
     */
    @Test
    void getUserById_NotFound() {
        // Mock repository to return empty Optional
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Expect a ResourceNotFoundException to be thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        // Verify the exception message
        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }
}
