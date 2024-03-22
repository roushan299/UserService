package com.example.UserService.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "Provide a valid username or email")
    private String usernameOrEmail;

    @NotNull(message = "Provide a valid password")
    private String password;
}
