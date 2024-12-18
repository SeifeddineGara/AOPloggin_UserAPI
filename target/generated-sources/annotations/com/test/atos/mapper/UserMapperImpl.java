package com.test.atos.mapper;

import com.test.atos.dto.UserRequest;
import com.test.atos.dto.UserResponse;
import com.test.atos.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-17T20:44:27+0100",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( dto.getUsername() );
        user.birthdate( dto.getBirthdate() );
        user.countryOfResidence( dto.getCountryOfResidence() );
        user.phoneNumber( dto.getPhoneNumber() );
        user.gender( dto.getGender() );

        return user.build();
    }

    @Override
    public UserResponse toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.birthdate( user.getBirthdate() );
        userResponse.countryOfResidence( user.getCountryOfResidence() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.gender( user.getGender() );

        return userResponse.build();
    }
}
