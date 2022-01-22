package com.mwyrzyk.backendmessagingapp.service;

import com.mwyrzyk.backendmessagingapp.exception.NotFoundException;
import com.mwyrzyk.backendmessagingapp.model.User;
import com.mwyrzyk.backendmessagingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mwyrzyk.backendmessagingapp.factory.UserFactory.getUserWithId;
import static com.mwyrzyk.backendmessagingapp.factory.UserFactory.getUserWithIdAndNickname;
import static com.mwyrzyk.backendmessagingapp.factory.UserFactory.getUserWithNickname;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void shouldCreateUserSuccessfully() {
        User userToSave = getUserWithNickname();

        when(userRepository.save(userToSave)).thenReturn(getUserWithIdAndNickname());
        User createdUser = userService.createUser(userToSave);

        assertNotNull(createdUser.getId());
        assertEquals(userToSave.getNickname(), createdUser.getNickname());
    }

    @Test
    public void shouldFetchUserSuccessfully() {
        User userToFetch = getUserWithId();

        when(userRepository.findById(any())).thenReturn(Optional.of(getUserWithIdAndNickname()));
        User fetchedUser = userService.fetch(userToFetch);

        assertNotNull(fetchedUser.getNickname());
        assertEquals(userToFetch.getId(), fetchedUser.getId());
    }
    @Test
    public void shouldThrowExceptionWhenFetchingNoExistingUser() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.fetch(getUserWithId()));
    }

}
