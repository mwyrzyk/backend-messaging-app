package com.mwyrzyk.backendmessagingapp.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingQueueConfiguration {

    @Bean
    public Queue messagingQueue() {
        return new Queue("messaging-queue");
    }

}
