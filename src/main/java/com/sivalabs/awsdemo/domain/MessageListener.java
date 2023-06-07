package com.sivalabs.awsdemo.domain;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MessageListener {
    private final MessageRepository messageRepository;

    @SqsListener(value = "${app.queueName}")
    public void handle(Message message) {
        log.info("Received message with uuid:{}, content:{}",message.getUuid(), message.getContent());
        messageRepository.save(message);
    }
}
