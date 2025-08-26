package com.anhltn.user_service.consumer;

import com.anhltn.common.event.UserRegisteredEvent;
import com.anhltn.user_service.entity.User;
import com.anhltn.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRegisteredConsumer {

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "auth-service.user.created", groupId = "user-registered-group-consumer")
    @Transactional(rollbackOn = Exception.class)
    public void consumeUserRegistered(UserRegisteredEvent event) {
        if (event.getId() == null || event.getId() == 0) return;

        User user = new User();
        user.setEmail(event.getEmail());
        user.setPassword(event.getPassword());
        user.setPhoneNumber(event.getPhoneNumber());
        user.setFullName(event.getFullName());
        user.setRoleName(event.getRole());

        userRepository.save(user);
    }
}
