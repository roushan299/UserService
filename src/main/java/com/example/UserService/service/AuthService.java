package com.example.UserService.service;

import com.example.UserService.dto.LoginRequest;
import com.example.UserService.dto.LoginResponse;
import com.example.UserService.dto.UserRequest;
import com.example.UserService.exception.EmailAlreadyExitsException;
import com.example.UserService.exception.UsernameAlreadyExitException;
import com.example.UserService.model.Role;
import com.example.UserService.model.User;
import com.example.UserService.repository.RoleRepository;
import com.example.UserService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        //verify the user and create the login token and return login response;
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsernameOrEmail());
        if(optionalUser.isEmpty()){
            optionalUser = userRepository.findByEmail(loginRequest.getUsernameOrEmail());
        }
        User user = optionalUser.get();
        if(user.getPassword().equals(loginRequest.getPassword())){
            LoginResponse loginResponse = LoginResponse.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(user.getPassword())
                    .build();
            return loginResponse;
        }
        LoginResponse loginResponse = LoginResponse.builder()
                    .username("Its a failure")
                    .build();
            return loginResponse;
    }

    public ResponseEntity<Object> signUpUser(UserRequest userRequest) throws UsernameAlreadyExitException, EmailAlreadyExitsException {
        //verify if user already exits
        if(exitsByUserName(userRequest.getUsername())){
            log.info("Username is already taken");
//            ResponseEntity<Object> response = new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
//            return response;
            throw new UsernameAlreadyExitException("Username is already taken");
        }

        if(exitsByEmailId(userRequest.getEmail())){
            log.info("Email is already taken");
//            ResponseEntity<Object> response = new ResponseEntity<>("Email has already been taken", HttpStatus.BAD_REQUEST);
//            return response;
            throw new EmailAlreadyExitsException("Email has already been taken");
        }

        //if not then create this user, and response Http.ok
        //Role mapping for a new user;

        Set<Role> roles = userRequest.getRoles();
        //Before saving user save the roles and update the roles in the user model
        roles = saveRoleAndUpdateInSet(roles);
        User user = User.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .roles(roles)
                .build();

        user = userRepository.save(user);
        if(user.getId()!=null) {
            log.info("User has been created");
            ResponseEntity<Object> response = new ResponseEntity<>(user.getUsername() + " has been created", HttpStatus.CREATED);
            return response;
        }
        ResponseEntity<Object> response = new ResponseEntity<>("Not able tp create user", HttpStatus.BAD_REQUEST);
        return response;
    }

    public Set<Role> saveRoleAndUpdateInSet(Set<Role> roles) {
        Set<Role> roleForUser = new HashSet<>();
        for(Role role: roles){
            if(roleExits(role.getName())){
                Role roleFromDB = roleRepository.findByName(role.getName()).get();
                roleForUser.add(roleFromDB);
            }else{
                role = roleRepository.save(role);
                roleForUser.add(role);
            }
        }
        return roleForUser;
    }

    private boolean roleExits(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.isPresent();
    }

    private boolean exitsByEmailId(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean exitsByUserName(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        return user.isPresent();
    }

}
