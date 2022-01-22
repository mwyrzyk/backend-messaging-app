package com.mwyrzyk.backendmessagingapp.repository;

import com.mwyrzyk.backendmessagingapp.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
