package com.example.UserService.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "roles")
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
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name= "roles_id"))
    @JsonIdentityReference
    private Set<User> users;

}
