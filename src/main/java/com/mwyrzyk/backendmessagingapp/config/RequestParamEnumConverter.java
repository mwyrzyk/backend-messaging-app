package com.mwyrzyk.backendmessagingapp.config;

import com.mwyrzyk.backendmessagingapp.dto.request.MessageStatus;
import org.springframework.core.convert.converter.Converter;

public class RequestParamEnumConverter implements Converter<String, MessageStatus> {

        @Override
        public MessageStatus convert(String source) {
            return MessageStatus.valueOf(source.toUpperCase());
        }
}
