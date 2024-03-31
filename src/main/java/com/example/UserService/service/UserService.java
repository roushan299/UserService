package com.example.UserService.service;


import com.example.UserService.dto.UserRequest;
import com.example.UserService.dto.UserResponse;
import com.example.UserService.exception.EmailAlreadyExitsException;
import com.example.UserService.exception.UserNotFoundException;
import com.example.UserService.exception.UsernameAlreadyExitException;
import com.example.UserService.model.Role;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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


    public UserResponse getUserById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User doesn't exit with id:"+userId);
        }
        User user = optionalUser.get();
        return mapToUserResponse(user);
    }

    public ResponseEntity<Object> createUser(UserRequest userRequest) throws UsernameAlreadyExitException, EmailAlreadyExitsException {
        ResponseEntity<Object> response = authService.signUpUser(userRequest);
        return response;
    }

    public ResponseEntity<Object> deleteUser(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        ResponseEntity<Object> response;
        if(optionalUser.isEmpty()){
//            response = new ResponseEntity<>("No user exists", HttpStatus.BAD_REQUEST);
//            return response;
            throw new UserNotFoundException("User doesn't exit with id:"+userId);
        }
        User user = optionalUser.get();
        userRepository.delete(user);
        response = new ResponseEntity<>("User has been deleted", HttpStatus.OK);
        return response;
    }

    public ResponseEntity<Object> updateUser(Long userId, UserRequest userRequest) throws UserNotFoundException {
        Optional<User> optionalUseruser = userRepository.findById(userId);
        ResponseEntity<Object> response;
        if(optionalUseruser.isEmpty()){
//            response = new ResponseEntity<>("No user exists", HttpStatus.BAD_REQUEST);
//            return response;
            throw new UserNotFoundException("User doesn't exit with id:"+userId);
        }
        //update set of the role
        Set<Role> roles = authService.saveRoleAndUpdateInSet(userRequest.getRoles());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userRequest.getPassword());
        User user = optionalUseruser.get();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(password);
        user.setRoles(roles);
        userRepository.save(user);
        response = new ResponseEntity<>("User is updated", HttpStatus.OK);
        return response;
    }

    public List<UserResponse> getAllUserByKeywords(String keywords) {
        List<User> userList = userRepository.findUserByNameKeyword(keywords);
        if(userList.isEmpty()){
            userList = userRepository.findUserByUsernameKeyword(keywords);
        }
      return userList.stream().map(this::mapToUserResponse).collect(Collectors.toList());
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
