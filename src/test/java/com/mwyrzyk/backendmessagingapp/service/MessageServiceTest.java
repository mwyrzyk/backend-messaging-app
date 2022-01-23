package com.mwyrzyk.backendmessagingapp.service;

import com.mwyrzyk.backendmessagingapp.dto.request.MessageStatus;
import com.mwyrzyk.backendmessagingapp.exception.NotFoundException;
import com.mwyrzyk.backendmessagingapp.model.Message;
import com.mwyrzyk.backendmessagingapp.model.User;
import com.mwyrzyk.backendmessagingapp.queue.MessagingSender;
import com.mwyrzyk.backendmessagingapp.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getFilledMessageWithId;
import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getMessageToSend;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

  @Mock
  MessageRepository messageRepository;

  @Mock
  UserService userService;

  @Mock
  MessagingSender messagingSender;

  @InjectMocks
  MessageService messageService;

  @Test
  public void shouldSendMessageSuccessfully() {
    Message messageToSend = getMessageToSend(1L, 2L);

    when(userService.fetch(any(User.class))).thenAnswer(returnsFirstArg());
    when(messageRepository.save(messageToSend)).thenReturn(getFilledMessageWithId());

    Message sentMessage = messageService.sendMessage(messageToSend);

    assertNotNull(sentMessage.getId());
    assertEquals(messageToSend.getContent(), sentMessage.getContent());
    verify(messagingSender, times(1)).send(any());
  }

  @Test
  public void shouldThrowExceptionWhenSendingMessageToYourself() {
    when(userService.fetch(any(User.class))).thenAnswer(returnsFirstArg());

    assertThrows(IllegalArgumentException.class,
        () -> messageService.sendMessage(getMessageToSend(1L, 1L)));
    verify(messagingSender, times(0)).send(any());
  }

  @Test
  public void shouldThrowExceptionWhenUserNotExists() {
    when(userService.fetch(any())).thenThrow(new NotFoundException("User not found."));

    assertThrows(NotFoundException.class, () -> messageService.sendMessage(getMessageToSend(1L, 1L)));
    verify(messagingSender, times(0)).send(any());
  }

  @Test
  public void shouldReturnAllSentMessagesWithoutSenderIdProvided() {
    when(messageRepository.findAllBySenderId(1L)).thenReturn(List.of(getFilledMessageWithId()));

    List<Message> messages = messageService.getMessages(null, MessageStatus.SENT,1L);

    assertEquals(1, messages.size());
  }

  @Test
  public void shouldReturnAllSentMessagesWithSenderIdProvided() {
    when(messageRepository.findAllBySenderId(1L)).thenReturn(List.of(getFilledMessageWithId()));

    List<Message> messages = messageService.getMessages(1L, MessageStatus.SENT,1L);

    assertEquals(1, messages.size());
  }

  @Test
  public void shouldThrowExceptionWhenSenderIdNotEqualsUserIdForSentStatus() {
    assertThrows(IllegalArgumentException.class, () -> messageService.getMessages(1L, MessageStatus.SENT, 2L));
  }

  @Test
  public void shouldReturnAllReceivedMessages() {
    when(messageRepository.findAllReceived(null, 2L)).thenReturn(List.of(getFilledMessageWithId()));

    List<Message> messages = messageService.getMessages(null, MessageStatus.RECEIVED,2L);

    assertEquals(1, messages.size());
  }

  @Test
  public void shouldReturnReceivedMessagesFromParticularUser() {
    when(messageRepository.findAllReceived(1L, 2L)).thenReturn(List.of(getFilledMessageWithId()));

    List<Message> messages = messageService.getMessages(1L, MessageStatus.RECEIVED,2L);

    assertEquals(1, messages.size());
  }

  @Test
  public void shouldThrowExceptionWhenStatusIsNull() {
    assertThrows(IllegalArgumentException.class, () -> messageService.getMessages(1L, null, 2L));
  }
}
