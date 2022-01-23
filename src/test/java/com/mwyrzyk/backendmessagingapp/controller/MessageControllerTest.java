package com.mwyrzyk.backendmessagingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwyrzyk.backendmessagingapp.dto.request.MessageRequestDto;
import com.mwyrzyk.backendmessagingapp.dto.request.MessageStatus;
import com.mwyrzyk.backendmessagingapp.model.Message;
import com.mwyrzyk.backendmessagingapp.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getFilledMessageWithId;
import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getMessageRequestDtoWithoutContent;
import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getMessageRequestDtoWithoutReceiver;
import static com.mwyrzyk.backendmessagingapp.factory.MessageFactory.getValidMessageRequestDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MessageService messageService;

    @Test
    public void shouldReturnMessage() throws Exception{
        when(messageService.sendMessage(any(Message.class))).thenReturn(getFilledMessageWithId());

        createMessage(getValidMessageRequestDto(), 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.content").value("test"))
                .andExpect(jsonPath("$.senderId").value(1L))
                .andExpect(jsonPath("$.receiverId").value(2L));
    }

    @Test
    public void shouldThrowExceptionWhenContentIsNotProvided() throws Exception {
        createMessage(getMessageRequestDtoWithoutContent(), 1L)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowExceptionWhenUserHeaderIsNotProvided() throws Exception {
        createMessage(getValidMessageRequestDto(), null)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowExceptionWhenReceiverIsNotProvided() throws Exception {
        createMessage(getMessageRequestDtoWithoutReceiver(), 1L)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnAllSentMessagesWithoutSenderId() throws Exception {
        when(messageService.getMessages(null, MessageStatus.SENT, 1L)).thenReturn(List.of(getFilledMessageWithId()));

        getMessages(null, "sent", "1")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].content").value("test"))
            .andExpect(jsonPath("$[0].senderId").value(1L))
            .andExpect(jsonPath("$[0].receiverId").value(2L));
    }

    @Test
    public void shouldReturnAllSentMessagesWithSenderId() throws Exception {
        when(messageService.getMessages(1L, MessageStatus.SENT, 1L)).thenReturn(List.of(getFilledMessageWithId()));

        getMessages("1", "sent", "1")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].content").value("test"))
            .andExpect(jsonPath("$[0].senderId").value(1L))
            .andExpect(jsonPath("$[0].receiverId").value(2L));
    }

    @Test
    public void shouldReturnAllReceived() throws Exception {
        when(messageService.getMessages(null, MessageStatus.RECEIVED, 2L)).thenReturn(List.of(getFilledMessageWithId()));

        getMessages(null, "received", "2")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].content").value("test"))
            .andExpect(jsonPath("$[0].senderId").value(1L))
            .andExpect(jsonPath("$[0].receiverId").value(2L));
    }

    @Test
    public void shouldReturnAllReceivedForParticularUser() throws Exception {
        when(messageService.getMessages(1L, MessageStatus.RECEIVED, 2L)).thenReturn(List.of(getFilledMessageWithId()));

        getMessages("1", "received", "2")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].content").value("test"))
            .andExpect(jsonPath("$[0].senderId").value(1L))
            .andExpect(jsonPath("$[0].receiverId").value(2L));
    }

    @Test
    public void shouldThrowExceptionWhenUserHeaderIsNotProvidedForGetMessages() throws Exception {
        getMessages("1", "received", null)
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowExceptionWhenStatusIsNull() throws Exception {
        getMessages("1", null, "2")
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowExceptionWhenStatusIsNotEnumValue() throws Exception {
        getMessages("1", "test", "2")
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowExceptionWhenSenderIdIsNotNumber() throws Exception {
        getMessages("test", "received", "2")
            .andExpect(status().is4xxClientError());
    }

    private ResultActions getMessages(String senderId, String status, String userId) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/messages");

        if (senderId != null) {
            requestBuilder.queryParam("senderId", senderId);
        }

        if (status != null) {
            requestBuilder.queryParam("status", status);
        }

        if (userId != null) {
            requestBuilder.header("User-Id", userId);
        }

        return mockMvc.perform(requestBuilder);
    }

    private ResultActions createMessage(MessageRequestDto messageRequestDto, Long userId) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/messages")
                .content(new ObjectMapper().writeValueAsString(messageRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        if (userId != null) {
            requestBuilder.header("User-Id", userId);
        }

        return mockMvc.perform(requestBuilder);
    }
}
