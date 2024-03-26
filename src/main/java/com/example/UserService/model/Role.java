package com.example.UserService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Data
@Builder
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 60)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name= "role_id"))
    @JsonBackReference
    private Set<User> users;

}
