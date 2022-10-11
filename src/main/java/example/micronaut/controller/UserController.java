package example.micronaut.controller;

import example.micronaut.entity.User;
import example.micronaut.repository.UserRepository;
import example.micronaut.repository.impl.UserRepositoryImpl;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.List;
import java.util.Optional;

@ExecuteOn(TaskExecutors.IO)
@Controller("/users")
public class UserController {

    protected final UserRepositoryImpl userRepository;

    public UserController(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Get("/getAllUsers")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Get("/getUserById/{id}")
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Put("/updateUsers/{id}")
    public void updateUser(Long id, @Body User user) {
        userRepository.update(id, user);
    }

    @Post("/saveUsers")
    public User addUser(@Body User user) {
        return userRepository.save(user);
    }

    @Delete("deleteUser/{id}")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

