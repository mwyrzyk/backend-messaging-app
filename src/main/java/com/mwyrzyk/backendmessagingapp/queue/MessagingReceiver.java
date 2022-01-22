package com.mwyrzyk.backendmessagingapp.queue;

import com.mwyrzyk.backendmessagingapp.dto.queue.MessageQueueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "messaging-queue")
public class MessagingReceiver {

    Logger logger = LoggerFactory.getLogger(MessagingReceiver.class);

    @RabbitHandler
    public void receive(MessageQueueDto message) {
        logger.info("Received: '{}'", message);
    }
}
