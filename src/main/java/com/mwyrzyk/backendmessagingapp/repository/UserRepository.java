package com.mwyrzyk.backendmessagingapp.repository;

import com.mwyrzyk.backendmessagingapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
