package com.example.UserService.dto;


import lombok.Data;

@Data
public class LoginResponse {

    private String username;
    private String email;
    private String token;
    private String name;
    private Integer id;


}
