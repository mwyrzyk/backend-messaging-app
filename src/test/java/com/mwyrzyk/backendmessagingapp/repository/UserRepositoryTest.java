package com.mwyrzyk.backendmessagingapp.repository;

import com.mwyrzyk.backendmessagingapp.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.mwyrzyk.backendmessagingapp.factory.UserFactory.getUserWithNickname;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldSaveUserSuccessfully() {
        User userToSave = getUserWithNickname();

        User createdUser = userRepository.save(userToSave);

        assertNotNull(createdUser.getId());
        assertEquals(userToSave.getNickname(), createdUser.getNickname());
    }

    @Test
    public void shouldGenerateUniqueIdForEachUser() {
        User firstUserToSave = getUserWithNickname();
        User secondUserToSave = getUserWithNickname();


        User firstCreatedUser = userRepository.save(firstUserToSave);
        User secondCreatedUser = userRepository.save(secondUserToSave);

        assertNotEquals(firstCreatedUser.getId(), secondCreatedUser.getId());
    }

    @Test
    public void shouldCreateUserSuccessfullyWithRepeatedNickname() {
        User firstUserToSave = getUserWithNickname();
        User secondUserToSave = getUserWithNickname();


        User firstCreatedUser = userRepository.save(firstUserToSave);
        User secondCreatedUser = userRepository.save(secondUserToSave);

        assertEquals(firstCreatedUser.getNickname(), secondCreatedUser.getNickname());
    }

}
