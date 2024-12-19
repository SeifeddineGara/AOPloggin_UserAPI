package com.test.atos.mapper;

import com.test.atos.dto.UserRequest;
import com.test.atos.dto.UserResponse;
import com.test.atos.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Mapper interface for converting between User entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {



    /**
     * Converts a UserRequest DTO to a User entity.
     *
     * @param dto The UserRequest DTO.
     * @return The corresponding User entity.
     */
    User toEntity(UserRequest dto);

    /**
     * Converts a User entity to a UserResponse DTO.
     *
     * @param user The User entity.
     * @return The corresponding UserResponse DTO.
     */
    UserResponse toDto(User user);
}
