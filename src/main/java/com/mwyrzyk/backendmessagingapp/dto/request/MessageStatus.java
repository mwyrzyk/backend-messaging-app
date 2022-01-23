package com.mwyrzyk.backendmessagingapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageStatus {
  @JsonProperty("sent")
  SENT,
  @JsonProperty("received")
  RECEIVED
}
