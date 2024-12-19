package com.test.atos.dto;

import com.test.atos.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object for User responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The birthdate of the user.
     */
    private LocalDate birthdate;

    /**
     * The country where the user resides.
     */
    private String countryOfResidence;

    /**
     * The user's phone number.
     */
    private String phoneNumber;

    /**
     * The gender of the user.
     */
    private User.Gender gender;
}
