package com.mwyrzyk.backendmessagingapp.repository;

import com.mwyrzyk.backendmessagingapp.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

  @Query("SELECT m FROM message m WHERE m.receiver.id = :receiverId AND (:senderId IS NULL OR m.sender.id = :senderId)")
  List<Message> findAllReceived(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

  List<Message> findAllBySenderId(Long senderId);
}
