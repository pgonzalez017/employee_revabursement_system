package com.revature.ers.controller;

import com.revature.ers.authentication.AuthenticationRequest;
import com.revature.ers.authentication.AuthenticationResponse;
import com.revature.ers.dto.NewUserDTO;
import com.revature.ers.dto.UserDTO;
import com.revature.ers.service.MyUserDetailsService;
import com.revature.ers.service.UserService;
import com.revature.ers.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Page<UserDTO> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
                                     @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                     @RequestParam(value = "order", required = false, defaultValue = "ASC") String order) {
        return userService.getAllUsers(page, offset, sortBy, order);
    }

    @PostMapping()
    public UserDTO createUser(@RequestBody NewUserDTO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(NewUserDTO.newUserToUser().apply(user));
        return userService.createUser(NewUserDTO.newUserToUser().apply(user));
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable(name = "userId") int id){
        return userService.getUserById(id);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
        } catch(BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        final int userId = userService.getUserByUsername(request.getUsername()).getUserId();

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt,userId));
    }
}
