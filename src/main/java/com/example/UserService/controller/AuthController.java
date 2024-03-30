package com.example.UserService.controller;

import com.example.UserService.dto.LoginRequest;
import com.example.UserService.dto.LoginResponse;
import com.example.UserService.dto.UserRequest;
import com.example.UserService.exception.EmailAlreadyExitsException;
import com.example.UserService.exception.UsernameAlreadyExitException;
import com.example.UserService.repository.UserRepository;
import com.example.UserService.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.kerberos.KerberosTicket;
import java.security.cert.Certificate;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping(path = "/signin")
    public LoginResponse signIn(@RequestBody @Valid LoginRequest loginRequest){
        //authenticate and send back login response
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);

        return loginResponse;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<Object> signUp(@RequestBody @Valid UserRequest userRequest){
        //Sign up the user
        ResponseEntity<Object> response = null;
        try {
            response = authService.signUpUser(userRequest);
        } catch (UsernameAlreadyExitException | EmailAlreadyExitsException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

}

