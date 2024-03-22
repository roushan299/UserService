package com.example.UserService.controller;


import com.example.UserService.dto.UserRequest;
import com.example.UserService.dto.UserResponse;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import org.hibernate.annotations.Struct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {




    @GetMapping(path = "/all")
    public List<UserResponse> getAllUsers(){
        //TO DO
    }

    @GetMapping(path = "/{userId}")
    public UserResponse getUserById(@PathVariable Long userId){
        //TO DO
    }

    @GetMapping(path = "/search/{keywords}")
    public List<UserResponse> getAllUsersByKeywords(@PathVariable String keywords){
        //TO DO
    }

    @PutMapping(path = "/{userId}")
    public String updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest){
        //To do
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable Long userId){
        //To do
    }


}
