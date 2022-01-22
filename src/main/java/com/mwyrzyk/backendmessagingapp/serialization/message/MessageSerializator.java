package com.mwyrzyk.backendmessagingapp.serialization.message;

import com.mwyrzyk.backendmessagingapp.dto.queue.MessageQueueDto;
import com.mwyrzyk.backendmessagingapp.dto.request.MessageRequestDto;
import com.mwyrzyk.backendmessagingapp.dto.request.UserRequestDto;
import com.mwyrzyk.backendmessagingapp.dto.response.MessageResponseDto;
import com.mwyrzyk.backendmessagingapp.model.Message;
import com.mwyrzyk.backendmessagingapp.serialization.user.UserDeserializator;

public class MessageSerializator {

    public static MessageResponseDto toMessageResponseDto(Message message) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setId(message.getId());
        messageResponseDto.setContent(message.getContent());
        messageResponseDto.setReceiverId(message.getReceiver().getId());
        messageResponseDto.setSenderId(message.getSender().getId());

        return messageResponseDto;
    }

    public static MessageQueueDto toMessageQueueDto(Message message) {
        MessageQueueDto messageQueueDto = new MessageQueueDto();
        messageQueueDto.setId(message.getId());
        messageQueueDto.setContent(message.getContent());
        messageQueueDto.setReceiverId(message.getReceiver().getId());
        messageQueueDto.setSenderId(message.getSender().getId());

        return messageQueueDto;
    }
}
