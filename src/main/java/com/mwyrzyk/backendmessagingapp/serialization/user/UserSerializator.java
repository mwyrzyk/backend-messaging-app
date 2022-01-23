package com.mwyrzyk.backendmessagingapp.serialization.user;

import com.mwyrzyk.backendmessagingapp.dto.response.UserResponseDto;
import com.mwyrzyk.backendmessagingapp.model.User;

public class UserSerializator {

  public static UserResponseDto toUserDto(User user) {
    UserResponseDto userResponseDto = new UserResponseDto();
    userResponseDto.setId(user.getId());
    userResponseDto.setNickname(user.getNickname());

    return userResponseDto;
  }
}
