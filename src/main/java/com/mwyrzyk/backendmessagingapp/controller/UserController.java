package com.mwyrzyk.backendmessagingapp.controller;

import com.mwyrzyk.backendmessagingapp.dto.request.UserRequestDto;
import com.mwyrzyk.backendmessagingapp.dto.response.UserResponseDto;
import com.mwyrzyk.backendmessagingapp.serialization.user.UserDeserializator;
import com.mwyrzyk.backendmessagingapp.serialization.user.UserSerializator;
import com.mwyrzyk.backendmessagingapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(UserSerializator.toUserDto(userService.createUser(UserDeserializator.toUser(userRequestDto))));
    }

}
