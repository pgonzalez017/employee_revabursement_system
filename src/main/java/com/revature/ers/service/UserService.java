package com.revature.ers.service;

import com.revature.ers.dto.UserDTO;
import com.revature.ers.model.User;
import com.revature.ers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Page<UserDTO> getAllUsers(int page, int offset, String sortBy, String order){

        page = pageValidation(page);
        sortBy = sortByValidation(sortBy);
        offset = offsetValidation(offset);

        Page<User> users;
        if(order.equalsIgnoreCase("DESC"))
            users = userRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            users = userRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        return users.map(UserDTO.userToDTO());
    }

    public UserDTO createUser(User user){
        return UserDTO.userToDTO().apply(userRepository.save(user));
    }

    private String sortByValidation(String sort){
        switch(sort.toLowerCase(Locale.ROOT)){
            case "name":
                return "name";
            case "email":
                return "email";
            case "registration":
                return "registrationTime";
            case "lastLogin":
                return "lastLogin";
            case "active":
                return "isActive";
            case "authority":
                return "authority";
            default:
                return "userId";
        }
    }

    private int pageValidation(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    private int offsetValidation(int offset){
        if(offset != 5 && offset != 10  && offset != 25 && offset != 50){
            offset = 25;
        }
        return offset;
    }

    public UserDTO getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    public UserDTO getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    public UserDTO getUserByUsername(String username){
        return UserDTO.userToDTO().apply(userRepository.findByUsername(username));
    }
}
