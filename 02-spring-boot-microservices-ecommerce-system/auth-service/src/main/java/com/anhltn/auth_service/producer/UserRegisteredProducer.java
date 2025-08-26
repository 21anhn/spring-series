package com.anhltn.auth_service.producer;

import com.anhltn.common.event.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredProducer {

    @Autowired
    private KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void sendUserRegisteredEvent(UserRegisteredEvent event) {
        String topic = "auth-service.user.created";

        Message<UserRegisteredEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        kafkaTemplate.send(message);
    }
}
