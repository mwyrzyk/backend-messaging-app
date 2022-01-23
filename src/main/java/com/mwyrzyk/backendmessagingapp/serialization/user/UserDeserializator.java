package com.mwyrzyk.backendmessagingapp.serialization.user;

import com.mwyrzyk.backendmessagingapp.dto.request.UserRequestDto;
import com.mwyrzyk.backendmessagingapp.model.User;

public class UserDeserializator {
  public static User toUser(UserRequestDto userRequestDto) {
    User user = new User();
    user.setId(userRequestDto.getId());
    user.setNickname(userRequestDto.getNickname());

    return user;
  }
}
