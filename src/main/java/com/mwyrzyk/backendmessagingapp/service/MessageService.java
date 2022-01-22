package com.mwyrzyk.backendmessagingapp.service;

import com.mwyrzyk.backendmessagingapp.model.Message;
import com.mwyrzyk.backendmessagingapp.model.User;
import com.mwyrzyk.backendmessagingapp.queue.MessagingSender;
import com.mwyrzyk.backendmessagingapp.repository.MessageRepository;
import com.mwyrzyk.backendmessagingapp.serialization.message.MessageSerializator;
import org.springframework.stereotype.Service;

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

        Message persistedMessage =  messageRepository.save(message);

        messagingSender.send(MessageSerializator.toMessageQueueDto(persistedMessage));

        return persistedMessage;
    }
}
