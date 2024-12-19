package com.test.atos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a User entity in the system.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user. Must be unique and not null.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * The birthdate of the user. Must not be null.
     */
     @Column(nullable = false)
    private LocalDate birthdate;

    /**
     * The country where the user resides. Must not be null.
     */
    @Column(nullable = false)
    private String countryOfResidence;

    /**
     * The user's phone number. This field is optional.
     */
    private String phoneNumber;

    /**
     * The gender of the user. This field is optional.
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Enumeration for Gender options.
     */
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
