package com.mwyrzyk.backendmessagingapp.controller;

import com.mwyrzyk.backendmessagingapp.dto.request.MessageRequestDto;
import com.mwyrzyk.backendmessagingapp.dto.request.MessageStatus;
import com.mwyrzyk.backendmessagingapp.dto.response.MessageResponseDto;
import com.mwyrzyk.backendmessagingapp.serialization.message.MessageDeserializator;
import com.mwyrzyk.backendmessagingapp.serialization.message.MessageSerializator;
import com.mwyrzyk.backendmessagingapp.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/messages")
public class MessageController {
  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping
  public ResponseEntity<MessageResponseDto> createMessage(@Valid @RequestBody MessageRequestDto messageRequestDto,
      @RequestHeader("User-Id") @NotEmpty Long userId) {

    messageRequestDto.setSenderId(userId);

    return ResponseEntity.ok(MessageSerializator.toMessageResponseDto(
        messageService.sendMessage(MessageDeserializator.toMessage(messageRequestDto))));
  }

  @GetMapping
  public ResponseEntity<List<MessageResponseDto>> getMessages(@RequestParam(required = false) Long senderId,
      @RequestParam(name = "status") MessageStatus messageStatus,
      @RequestHeader("User-Id") @NotEmpty Long userId) {

    return ResponseEntity.ok(messageService.getMessages(senderId, messageStatus, userId).stream().map(
        MessageSerializator::toMessageResponseDto).collect(Collectors.toList()));
  }
}
