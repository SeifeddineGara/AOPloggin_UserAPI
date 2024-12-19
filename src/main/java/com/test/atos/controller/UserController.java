package com.test.atos.controller;

import com.test.atos.dto.UserRequest;
import com.test.atos.dto.UserResponse;
import com.test.atos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling User-related API requests.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService The UserService instance.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to register a new user.
     *
     * @param userRequest The UserRequest DTO containing user details.
     * @return A ResponseEntity containing the UserResponse DTO and HTTP status.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
         UserResponse userResponse = userService.registerUser(userRequest);
        // Return HTTP 201 Created status with the user details
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve user details by ID.
     *
     * @param id The unique identifier of the user.
     * @return A ResponseEntity containing the UserResponse DTO and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        // Return HTTP 200 OK status with the user details
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
