package com.mwyrzyk.backendmessagingapp.service;

import com.mwyrzyk.backendmessagingapp.exception.NotFoundException;
import com.mwyrzyk.backendmessagingapp.model.User;
import com.mwyrzyk.backendmessagingapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User fetch(User userToFetch) {
    Long userToFetchId = userToFetch.getId();
    return userRepository.findById(userToFetchId)
        .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", userToFetchId)));
  }
}
