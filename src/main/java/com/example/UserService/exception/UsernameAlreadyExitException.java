package com.example.UserService.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyExitException extends Exception{


    public UsernameAlreadyExitException(String usernameIsAlreadyTaken) {
        super(usernameIsAlreadyTaken);
    }
}
