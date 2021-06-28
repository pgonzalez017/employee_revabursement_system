package com.revature.ers.dto;

import com.revature.ers.model.Authority;
import com.revature.ers.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int userId;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private Authority authority;

    private Timestamp registrationTime;

    public static Function<User, UserDTO> userToDTO(){
        return user -> {
            if(user==null){
                throw new IllegalArgumentException("Parameter [user] cannot be null.");
            }
            return new UserDTO (
                    user.getUserId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getIsActive(),
                    user.getAuthority(),
                    user.getRegistrationTime()
            );
        };
    }

    public static Function<UserDTO, User> dtoToUser(){
        return userDTO -> {
            if(userDTO == null){
                throw new IllegalArgumentException("Parameter [userDTO] cannot be null.");
            }
            return new User(
                    userDTO.getUserId(),
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.isActive(),
                    userDTO.getAuthority(),
                    userDTO.getRegistrationTime()
            );
        };
    }
}
