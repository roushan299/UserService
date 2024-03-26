package com.example.UserService.service;


import com.example.UserService.dto.UserRequest;
import com.example.UserService.dto.UserResponse;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;


    public List<UserResponse> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }


    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        if(user==null){
            // throw error
        }
        return mapToUserResponse(user);
    }

    public ResponseEntity<Object> createUser(UserRequest userRequest) {
        ResponseEntity<Object> response = authService.signUpUser(userRequest);
        return response;
    }

    public ResponseEntity<Object> deleteUser(Long userId) {
        User user = userRepository.findById(userId).get();
        ResponseEntity<Object> response;
        if(user==null){
            response = new ResponseEntity<>("No user exists", HttpStatus.BAD_REQUEST);
            return response;
        }
        userRepository.delete(user);
        response = new ResponseEntity<>("User has been deleted", HttpStatus.OK);
        return response;
    }

    public ResponseEntity<Object> updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).get();
        ResponseEntity<Object> response;
        if(user==null){
            response = new ResponseEntity<>("No user exists", HttpStatus.BAD_REQUEST);
            return response;
        }
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRoles(userRequest.getRoles());

        userRepository.save(user);
        response = new ResponseEntity<>("User is updated", HttpStatus.OK);
        return response;
    }

    public List<UserResponse> getAllUserByKeywords(String keywords) {
        //TO DO
      return new ArrayList<UserResponse>();
    }


    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .username(user.getUsername())
                .roles(user.getRoles())
                .id(user.getId())
                .build();
        return userResponse;
    }

}
