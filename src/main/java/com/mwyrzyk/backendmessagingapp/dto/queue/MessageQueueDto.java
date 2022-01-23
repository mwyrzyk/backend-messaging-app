package com.mwyrzyk.backendmessagingapp.dto.queue;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageQueueDto implements Serializable {

  @Serial
  private static final long serialVersionUID = -2417084909693651866L;

  private Long id;

  @NotEmpty
  private String content;

  @NotEmpty
  private Long senderId;

  @NotEmpty
  private Long receiverId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public Long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  @Override
  public String toString() {
    return "MessageQueueDto{" +
        "id=" + id +
        ", content='" + content + '\'' +
        ", senderId=" + senderId +
        ", receiverId=" + receiverId +
        '}';
  }
}
