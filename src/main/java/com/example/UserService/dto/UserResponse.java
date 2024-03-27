package com.example.UserService.dto;


import com.example.UserService.model.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private String email;
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles != null ? roles : Collections.emptySet();
    }

}
