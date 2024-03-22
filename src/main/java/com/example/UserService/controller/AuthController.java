package com.example.UserService.controller;

import com.example.UserService.dto.LoginRequest;
import com.example.UserService.dto.LoginResponse;
import com.example.UserService.dto.UserRequest;
import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {


    @PostMapping(path = "/signin")
    public LoginResponse signIn(@RequestBody LoginRequest loginRequest){
        //To do
    }

    @PostMapping(path = "/signup")
    public String signUp(@RequestBody UserRequest userRequest){
        //To do
    }
}
