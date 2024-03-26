package com.example.UserService.service;

import com.example.UserService.dto.LoginRequest;
import com.example.UserService.dto.LoginResponse;
import com.example.UserService.dto.UserRequest;
import com.example.UserService.model.Role;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        //verify the user and create the login token and return login response;
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsernameOrEmail());
        if(optionalUser.isEmpty()){
            optionalUser = userRepository.findByEmail(loginRequest.getUsernameOrEmail());
        }
        User user = optionalUser.get();
        if(user.getPassword().equals(loginRequest.getPassword())){
            LoginResponse loginResponse = LoginResponse.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(user.getPassword())
                    .build();
            return loginResponse;
        }
        LoginResponse loginResponse = LoginResponse.builder()
                    .username("Its a failure")
                    .build();
            return loginResponse;
    }

    public ResponseEntity<Object> signUpUser(UserRequest userRequest) {
        //verify if user already exits
        if(exitsByUserName(userRequest.getName())){
            log.info("Username is already taken");
            ResponseEntity<Object> response = new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
            return response;
        }

        if(exitsByEmailId(userRequest.getEmail())){
            log.info("Email is already taken");
            ResponseEntity<Object> response = new ResponseEntity<>("Email has already been taken", HttpStatus.BAD_REQUEST);
            return response;
        }

        //if not then create this user, and response Http.ok
        //Role mapping for a new user;
        User user = User.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        for(Role role: userRequest.getRoles()){
            user.getRoles().add(role);
        }

        user = userRepository.save(user);
        if(user.getId()!=null) {
            log.info("User has been created");
            ResponseEntity<Object> response = new ResponseEntity<>(user.getUsername() + " has been created", HttpStatus.OK);
            return response;
        }
        ResponseEntity<Object> response = new ResponseEntity<>("Not able tp create user", HttpStatus.BAD_REQUEST);
        return response;
    }

    private boolean exitsByEmailId(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean exitsByUserName(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        return user.isPresent();
    }

}
