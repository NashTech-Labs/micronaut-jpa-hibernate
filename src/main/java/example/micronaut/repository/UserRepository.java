package example.micronaut.repository;

import example.micronaut.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

    List<User> findAll();

    void update(Long id, User user);
}

