package com.mwyrzyk.backendmessagingapp.queue;

import com.mwyrzyk.backendmessagingapp.dto.queue.MessageQueueDto;
import com.mwyrzyk.backendmessagingapp.dto.response.MessageResponseDto;
import com.mwyrzyk.backendmessagingapp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingSender {

    private final RabbitTemplate template;

    private final Queue queue;

   private final Logger logger = LoggerFactory.getLogger(MessagingSender.class);

    public MessagingSender(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void send(MessageQueueDto message) {
       template.convertAndSend(queue.getName(), message);
        logger.info("Sent: '{}'", message);
    }
}
