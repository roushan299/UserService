package com.example.UserService.controller;


import com.example.UserService.dto.UserRequest;
import com.example.UserService.dto.UserResponse;
import com.example.UserService.exception.EmailAlreadyExitsException;
import com.example.UserService.exception.UserNotFoundException;
import com.example.UserService.exception.UsernameAlreadyExitException;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import com.example.UserService.service.UserService;
import jakarta.validation.Valid;
import org.hibernate.annotations.Struct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers(){
        List<UserResponse> userResponseList = userService.getAllUsers();
        return userResponseList;
    }

    @GetMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable Long userId){
        UserResponse userResponse = null;
        try {
            userResponse = userService.getUserById(userId);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userResponse;
    }

    @GetMapping(path = "/search/{keywords}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsersByKeywords(@PathVariable String keywords){
        List<UserResponse> userResponseList = userService.getAllUserByKeywords(keywords);
        return userResponseList;
    }

    @PutMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody @Valid UserRequest userRequest){
        ResponseEntity<Object> responseEntity = null;
        try {
            responseEntity = userService.updateUser(userId, userRequest);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return responseEntity;
    }

    @DeleteMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId){
        ResponseEntity<Object> response = null;
        try {
            response = userService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @PostMapping(path = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest){
        ResponseEntity<Object> response = null;
        try {
            response = userService.createUser(userRequest);
        } catch (UsernameAlreadyExitException | EmailAlreadyExitsException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
