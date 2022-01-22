package com.mwyrzyk.backendmessagingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwyrzyk.backendmessagingapp.dto.request.UserRequestDto;
import com.mwyrzyk.backendmessagingapp.model.User;
import com.mwyrzyk.backendmessagingapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.mwyrzyk.backendmessagingapp.factory.UserFactory.getUserWithIdAndNickname;
import static com.mwyrzyk.backendmessagingapp.factory.UserFactory.getValidUserRequestDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(getUserWithIdAndNickname());

        createUser(getValidUserRequestDto())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nickname").value("test"));

    }

    @Test
    public void shouldThrowExceptionWhenNicknameIsNotProvided() throws Exception {
        createUser(new UserRequestDto())
                .andExpect(status().is4xxClientError());
    }

    private ResultActions createUser(UserRequestDto userDto) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/users")
                        .content(new ObjectMapper().writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }
}
