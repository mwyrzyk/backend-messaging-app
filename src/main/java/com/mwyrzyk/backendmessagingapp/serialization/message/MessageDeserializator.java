package com.mwyrzyk.backendmessagingapp.serialization.message;

import com.mwyrzyk.backendmessagingapp.dto.request.MessageRequestDto;
import com.mwyrzyk.backendmessagingapp.dto.request.UserRequestDto;
import com.mwyrzyk.backendmessagingapp.model.Message;
import com.mwyrzyk.backendmessagingapp.serialization.user.UserDeserializator;

public class MessageDeserializator {
  public static Message toMessage(MessageRequestDto messageRequestDto) {
    Message message = new Message();
    message.setContent(messageRequestDto.getContent());

    UserRequestDto receiver = new UserRequestDto();
    receiver.setId(messageRequestDto.getReceiverId());
    message.setReceiver(UserDeserializator.toUser(receiver));

    UserRequestDto sender = new UserRequestDto();
    sender.setId(messageRequestDto.getSenderId());
    message.setSender(UserDeserializator.toUser(sender));

    return message;
  }

}
