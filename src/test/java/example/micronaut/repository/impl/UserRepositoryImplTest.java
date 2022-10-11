package example.micronaut.repository.impl;

import example.micronaut.entity.User;
import example.micronaut.repository.UserRepository;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class UserRepositoryImplTest {

    private BlockingHttpClient blockingClient;

    @Inject
    UserRepository userRepository;

    @Inject
    @Client("/")
    HttpClient client;

    @BeforeEach
    void setup() {
        blockingClient = client.toBlocking();
    }

    @Test
    public void save() {
        User user = new User();
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        User response = userRepository.save(user);
        assertEquals(1L, response.getId());
    }

    @Test
    public void update() {
        User user = new User();
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        int response = userRepository.update(1L, new User(1L, "JJ", "George", "123@gmail.com"));
        assertEquals(0, response);
    }

    @Test
    public void delete() {
        User user = new User();
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        userRepository.deleteById(1L);
    }

    @Test
    public void getById() {
        User user = new User();
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        userRepository.findById(1L);
    }

    @Test
    public void getUsers() {
        User user = new User();
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        userRepository.findAll();
    }

}

