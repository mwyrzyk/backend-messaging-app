package com.mwyrzyk.backendmessagingapp.repository;

import com.mwyrzyk.backendmessagingapp.model.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getMessageToSend;
import static com.mwyrzyk.backendmessagingapp.factory.UserFactory.getUserWithIdAndNickname;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MessageRepositoryTest {

  @Autowired
  MessageRepository messageRepository;

  @BeforeAll
  public static void createUsers(@Autowired UserRepository userRepository) {
    userRepository.saveAll(
        Arrays.asList(getUserWithIdAndNickname(1L), getUserWithIdAndNickname(2L),
            getUserWithIdAndNickname(3L)));
  }

  @AfterEach
  public void destroyAll() {
    messageRepository.deleteAll();
  }

  @Test
  public void shouldSaveMessageSuccessfully() {
    Message messageToSave = getMessageToSend(1L, 2L);

    Message createdMessage = messageRepository.save(messageToSave);

    assertNotNull(createdMessage.getId());
    assertEquals(createdMessage.getContent(), messageToSave.getContent());
  }

  @Test
  public void shouldGenerateUniqueIdForEachMessage() {
    Message firstMessageToSave = getMessageToSend(1L, 2L);
    Message secondMessageToSave = getMessageToSend(1L, 2L);

    Message firstCreatedMessage = messageRepository.save(firstMessageToSave);
    Message secondCreatedMessage = messageRepository.save(secondMessageToSave);

    assertNotEquals(firstCreatedMessage.getId(), secondCreatedMessage.getId());
  }

  @Test
  public void shouldReturnAllSentMessages() {
    messageRepository.save(getMessageToSend(1L, 2L));
    messageRepository.save(getMessageToSend(1L, 2L));

    List<Message> messages = messageRepository.findAllBySenderId(1L);

    assertEquals(2, messages.size());
  }

  @Test
  public void shouldReturnAllEmptySentMessagesListWhenSenderIdNotExists() {
    messageRepository.save(getMessageToSend(1L, 2L));
    messageRepository.save(getMessageToSend(1L, 2L));

    List<Message> messages = messageRepository.findAllBySenderId(99L);

    assertEquals(0, messages.size());
  }

  @Test
  public void shouldReturnAllReceivedMessages() {
    messageRepository.save(getMessageToSend(1L, 2L));
    messageRepository.save(getMessageToSend(1L, 2L));

    List<Message> messages = messageRepository.findAllReceived(null, 2L);

    assertEquals(2, messages.size());
  }

  @Test
  public void shouldReturnReceivedMessagesFromParticularUser() {
    messageRepository.save(getMessageToSend(1L, 3L));
    messageRepository.save(getMessageToSend(2L, 3L));

    List<Message> messages = messageRepository.findAllReceived(2L, 3L);


    assertEquals(1, messages.size());
  }

  @Test
  public void shouldReturnEmptyReceivedMessagesListWhenReceiverIdNotExists() {
    messageRepository.save(getMessageToSend(1L, 3L));
    messageRepository.save(getMessageToSend(2L, 3L));

    List<Message> messages = messageRepository.findAllReceived(2L, 4L);


    assertEquals(0, messages.size());
  }

  @Test
  public void shouldReturnEmptyReceivedMessagesListWhenSenderIdNotExists() {
    messageRepository.save(getMessageToSend(1L, 3L));
    messageRepository.save(getMessageToSend(2L, 3L));

    List<Message> messages = messageRepository.findAllReceived(99L, 4L);


    assertEquals(0, messages.size());
  }

}
