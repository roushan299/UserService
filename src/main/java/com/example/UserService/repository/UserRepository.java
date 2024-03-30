package com.example.UserService.repository;

import com.example.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.* FROM user as u WHERE u.name  like  %?1% ", nativeQuery = true)
    List<User> findUserByNameKeyword(String keyword);

    @Query(value = "SELECT u.* FROM user as u WHERE u.username  like  %?1% ", nativeQuery = true)
    List<User> findUserByUsernameKeyword(String keyword);
}

