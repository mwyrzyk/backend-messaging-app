package com.mwyrzyk.backendmessagingapp.factory;

import com.mwyrzyk.backendmessagingapp.dto.request.UserRequestDto;
import com.mwyrzyk.backendmessagingapp.model.User;

public class UserFactory {

  public static User getUserWithId(Long userId) {
    User user = new User();
    user.setId(userId);

    return user;
  }

  public static User getUserWithNickname() {
    User user = new User();
    user.setNickname("test");

    return user;
  }

  public static User getUserWithIdAndNickname(Long userId) {
    User user = new User();
    user.setId(userId);
    user.setNickname("test");

    return user;
  }

  public static UserRequestDto getValidUserRequestDto() {
    UserRequestDto userRequestDto = new UserRequestDto();
    userRequestDto.setNickname("test");

    return userRequestDto;
  }

}
