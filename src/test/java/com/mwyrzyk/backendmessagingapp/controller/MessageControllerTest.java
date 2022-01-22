package com.mwyrzyk.backendmessagingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwyrzyk.backendmessagingapp.dto.request.MessageRequestDto;
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
    public void shouldThrowExceptionWhenSenderHeaderIsNotProvided() throws Exception {
        createMessage(getValidMessageRequestDto(), null)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowExceptionWhenReceiverIsNotProvided() throws Exception {
        createMessage(getMessageRequestDtoWithoutReceiver(), 1L)
                .andExpect(status().is4xxClientError());
    }

    private ResultActions createMessage(MessageRequestDto messageRequestDto, Long senderId) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/messages")
                .content(new ObjectMapper().writeValueAsString(messageRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        if (senderId != null) {
            requestBuilder.header("Sender-Id", senderId);
        }

        return mockMvc.perform(requestBuilder);
    }
}
