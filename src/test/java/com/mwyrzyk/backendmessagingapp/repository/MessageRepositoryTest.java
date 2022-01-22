package com.mwyrzyk.backendmessagingapp.repository;

import com.mwyrzyk.backendmessagingapp.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getValidMessageToSend;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void shouldSaveMessageSuccessfully() {
        Message messageToSave = getValidMessageToSend();

        Message createdMessage = messageRepository.save(messageToSave);

        assertNotNull(createdMessage.getId());
        assertEquals(createdMessage.getContent(), messageToSave.getContent());
    }

    @Test
    public void shouldGenerateUniqueIdForEachMessage() {
        Message firstMessageToSave = getValidMessageToSend();
        Message secondMessageToSave = getValidMessageToSend();

        Message firstCreatedMessage = messageRepository.save(firstMessageToSave);
        Message secondCreatedMessage = messageRepository.save(secondMessageToSave);

        assertNotEquals(firstCreatedMessage.getId(), secondCreatedMessage.getId());
    }

}
