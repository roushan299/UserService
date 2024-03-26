package com.example.UserService.dto;


import com.example.UserService.model.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserRequest {

    @NotNull(message = "Provide a valid name")
    private String name;

    @NotNull(message = "Please provide valid username")
    private String username;

    @NotNull(message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Provide a valid password")
    private String password;

    private Set<Role> roles;
}
