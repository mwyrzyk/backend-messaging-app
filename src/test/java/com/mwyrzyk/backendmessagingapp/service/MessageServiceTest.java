package com.mwyrzyk.backendmessagingapp.service;

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

import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getFilledMessageWithId;
import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getMessageWithSenderEqualToReceiver;
import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getValidMessageToSend;
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
        Message messageToSend = getValidMessageToSend();

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

        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(getMessageWithSenderEqualToReceiver()));
        verify(messagingSender, times(0)).send(any());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotExists() {
        when(userService.fetch(any())).thenThrow(new NotFoundException("User not found."));

        assertThrows(NotFoundException.class, () -> messageService.sendMessage(getMessageWithSenderEqualToReceiver()));
        verify(messagingSender, times(0)).send(any());
    }
}
