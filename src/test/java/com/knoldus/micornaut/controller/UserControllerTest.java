package com.knoldus.micornaut.controller;


import com.knoldus.micornaut.entity.User;
import com.knoldus.micornaut.repository.UserRepository;
import com.knoldus.micornaut.repository.impl.UserRepositoryImpl;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.hibernate.BaseSessionEventListener;
import org.hibernate.SessionEventListener;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
class UserControllerTest {

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
    void saveUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpRequest<?> request = HttpRequest.POST("/users/saveUsers", user);
        HttpResponse<?> response = blockingClient.exchange(request);
        userRepository.save(user);
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void saveUserFailure() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> blockingClient.exchange(HttpRequest.POST("users/", user)));
        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpRequest<?> request = HttpRequest.PUT("/users/updateUsers/" + user.getId(), new User(1L, "JJ", "George", "123@gmail.com"));
        HttpResponse<?> response = blockingClient.exchange(request);
        userRepository.update(user.getId(), user);
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void updateUserFailure() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> blockingClient.exchange(HttpRequest.PUT("users/", user)));
        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpRequest<?> request = HttpRequest.DELETE("/users/deleteUser/" + user.getId());
        HttpResponse<?> response = blockingClient.exchange(request);
        userRepository.deleteById(user.getId());
        assertEquals(HttpStatus.OK, response.getStatus());
    }


    @Test
    void deleteUserFailure() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> blockingClient.exchange(HttpRequest.DELETE("users/", user)));
        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void getUsers() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpRequest<?> request = HttpRequest.GET("/users/get");
        HttpResponse<?> response = blockingClient.exchange(request);
        userRepository.findAll();
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void getUsersFailure() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> blockingClient.exchange(HttpRequest.GET("users/")));
        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void getUserById() {
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        doNothing().when(sessionDelegatorBaseImpl).addEventListeners((SessionEventListener[]) any());
        sessionDelegatorBaseImpl.addEventListeners(new BaseSessionEventListener());

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.find((Class<User>) any(), (Object) any())).thenReturn(user);
        assertTrue((new UserController(new UserRepositoryImpl(sessionDelegatorBaseImpl1))).getUser(1L).isPresent());
        verify(sessionDelegatorBaseImpl).addEventListeners((SessionEventListener[]) any());
        verify(sessionDelegatorBaseImpl1).find((Class<User>) any(), (Object) any());
    }

    @Test
    void getUserByIdFailure() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("George");
        user.setLastName("John");
        user.setEmailId("john@gmail.com");
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> blockingClient.exchange(HttpRequest.GET("users/" + user.getId())));
        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }
}

