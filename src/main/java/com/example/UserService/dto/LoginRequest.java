package com.example.UserService.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @NotNull(message = "Provide a valid username or email")
    private String usernameOrEmail;

    @NotNull(message = "Provide a valid password")
    private String password;
}
