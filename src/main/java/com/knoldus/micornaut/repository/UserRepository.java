package com.knoldus.micornaut.repository;

import com.knoldus.micornaut.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

    List<User> findAll();

    int update(Long id, User user);
}

