package com.test.atos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.atos.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    /**
     * The desired username for the user. Must not be blank.
     */
    @NotBlank(message = "Username is mandatory")
    private String username;

    /**
     * The birthdate of the user. Must not be null and must be a past date.
     */
    @NotNull(message = "Birthdate is mandatory")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    /**
     * The country where the user resides. Must not be blank.
     */
    @NotBlank(message = "Country of residence is mandatory")
    private String countryOfResidence;

    /**
     * The user's phone number. Optional but must match the specified pattern if provided.
     */
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    /**
     * The gender of the user. Optional field.
     */
    private User.Gender gender;
}
