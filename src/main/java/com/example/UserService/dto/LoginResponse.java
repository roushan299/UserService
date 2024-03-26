package com.example.UserService.dto;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String username;
    private String email;
    private String token;
    private String name;
    private Long id;


}
