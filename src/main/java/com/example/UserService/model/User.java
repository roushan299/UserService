package com.example.UserService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles != null ? roles : Collections.emptySet();
    }



}
