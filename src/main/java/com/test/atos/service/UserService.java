package com.test.atos.service;

import com.test.atos.dto.UserRequest;
import com.test.atos.dto.UserResponse;
import com.test.atos.entity.User;
import com.test.atos.exception.ResourceNotFoundException;
import com.test.atos.exception.ValidationException;
import com.test.atos.mapper.UserMapper;
import com.test.atos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

/**
 * Service class that contains business logic for User operations.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Constructor for UserService.
     *
     * @param userRepository The UserRepository instance.
     * @param userMapper     The UserMapper instance.
     */
    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Registers a new user after validating the input.
     *
     * @param userRequest The UserRequest DTO containing user details.
     * @return The UserResponse DTO of the registered user.
     */
    public UserResponse registerUser(UserRequest userRequest) {
        // Perform custom validation
        validateUser(userRequest);

        // Check if the username already exists
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists");
        }

        // Convert DTO to entity
        User user = userMapper.toEntity(userRequest);

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Convert entity to DTO for response
        return userMapper.toDto(savedUser);
    }

    /**
     * Retrieves user details by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The UserResponse DTO containing user details.
     */
    public UserResponse getUserById(Long id) {
        // Fetch user from repository or throw exception if not found
        User user  = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Convert entity to DTO for response
        return userMapper.toDto(user);
    }

    /**
     * Validates the user input based on business rules.
     *
     * @param userRequest The UserRequest DTO to validate.
     */
    private void validateUser(UserRequest userRequest) {
        // Check if country is France
        if (!"France".equalsIgnoreCase(userRequest.getCountryOfResidence())) {
            throw new ValidationException("Only French residents can register");
        }

        // Check if user is adult (18+)
        LocalDate today = LocalDate.now();
        Period age = Period.between(userRequest.getBirthdate(), today);
        if (age.getYears() < 18) {
            throw new ValidationException("User must be at least 18 years old to register");
        }
    }
}
