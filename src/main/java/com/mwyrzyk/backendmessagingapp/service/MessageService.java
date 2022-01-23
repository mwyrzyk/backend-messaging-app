package com.mwyrzyk.backendmessagingapp.service;

import com.mwyrzyk.backendmessagingapp.dto.request.MessageStatus;
import com.mwyrzyk.backendmessagingapp.model.Message;
import com.mwyrzyk.backendmessagingapp.model.User;
import com.mwyrzyk.backendmessagingapp.queue.MessagingSender;
import com.mwyrzyk.backendmessagingapp.repository.MessageRepository;
import com.mwyrzyk.backendmessagingapp.serialization.message.MessageSerializator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

  private final MessageRepository messageRepository;
  private final UserService userService;
  private final MessagingSender messagingSender;

  public MessageService(MessageRepository messageRepository, UserService userService, MessagingSender messagingSender) {
    this.messageRepository = messageRepository;
    this.userService = userService;
    this.messagingSender = messagingSender;
  }

  public Message sendMessage(Message message) {
    User sender = userService.fetch(message.getSender());
    User receiver = userService.fetch(message.getReceiver());

    if (sender.getId().equals(receiver.getId())) {
      throw new IllegalArgumentException("Sending messages to yourself is forbidden");
    }
    message.setSender(sender);
    message.setReceiver(receiver);

    Message persistedMessage = messageRepository.save(message);

    messagingSender.send(MessageSerializator.toMessageQueueDto(persistedMessage));

    return persistedMessage;
  }

  public List<Message> getMessages(Long senderId, MessageStatus messageStatus, Long userId) {
    if (messageStatus == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }

    switch (messageStatus) {
      case RECEIVED:
        return messageRepository.findAllReceived(senderId, userId);
      case SENT:
        if (senderId != null && !senderId.equals(userId)) {
          throw new IllegalArgumentException("Checking messages sent by other users is forbidden");
        }

        return messageRepository.findAllBySenderId(userId);
      default:
        throw new IllegalArgumentException("Status not supported");
    }
  }
}
