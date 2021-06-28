package com.revature.ers.dto;

import com.revature.ers.model.Authority;
import com.revature.ers.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Authority authority;

    public static Function<NewUserDTO, User> newUserToUser(){
        return newUserDTO -> {
            if(newUserDTO == null){
                throw new IllegalArgumentException("Parameter [newUserDTO] cannot be null.");
            }

            return new User(
                    newUserDTO.getUsername(),
                    newUserDTO.getPassword(),
                    newUserDTO.getEmail(),
                    newUserDTO.getFirstName(),
                    newUserDTO.getLastName(),
                    newUserDTO.getAuthority()
            );
        };
    }
}
