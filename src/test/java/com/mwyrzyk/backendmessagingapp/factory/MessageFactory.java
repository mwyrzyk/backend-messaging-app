package com.mwyrzyk.backendmessagingapp.factory;


import com.mwyrzyk.backendmessagingapp.dto.request.MessageRequestDto;
import com.mwyrzyk.backendmessagingapp.model.Message;
import com.mwyrzyk.backendmessagingapp.model.User;

public class MessageFactory {

  public static Message getMessageToSend(Long senderId, Long receiverId) {
    User sender = new User();
    sender.setId(senderId);

    User receiver = new User();
    receiver.setId(receiverId);

    Message message = new Message();
    message.setContent("test");
    message.setSender(sender);
    message.setReceiver(receiver);

    return message;
  }

  public static Message getFilledMessageWithId() {
    User sender = new User();
    sender.setId(1L);

    User receiver = new User();
    receiver.setId(2L);

    Message message = new Message();
    message.setId(1L);
    message.setContent("test");
    message.setSender(sender);
    message.setReceiver(receiver);

    return message;
  }

  public static MessageRequestDto getValidMessageRequestDto() {
    MessageRequestDto message = new MessageRequestDto();
    message.setContent("test");
    message.setReceiverId(2L);

    return message;
  }

  public static MessageRequestDto getMessageRequestDtoWithoutContent() {
    MessageRequestDto message = new MessageRequestDto();
    message.setReceiverId(2L);

    return message;
  }

  public static MessageRequestDto getMessageRequestDtoWithoutReceiver() {
    MessageRequestDto message = new MessageRequestDto();
    message.setContent("test");

    return message;
  }
}
