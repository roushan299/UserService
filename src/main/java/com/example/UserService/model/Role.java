package com.example.UserService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 60, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name= "role_id"))
    @JsonBackReference
    private Set<User> users;

}
