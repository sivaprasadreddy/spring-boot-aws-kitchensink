package com.sivalabs.awsdemo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.awsdemo.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;
    private final ApplicationProperties properties;

    public Optional<Message> findByUuid(String uuid) {
        log.info("Fetching message with id:{}", uuid);
        return messageRepository.findByUuid(uuid);
    }

    public CompletableFuture<Void> send(Message message) {
        String queueName = properties.queueName();
        return this.sqsAsyncClient.getQueueUrl(request -> request.queueName(queueName))
                .thenApply(GetQueueUrlResponse::queueUrl)
                .thenCompose(queueUrl -> sendToUrl(queueUrl, message));
    }

    public CompletableFuture<Void> sendToUrl(String queueUrl, Message message) {
        return this.sqsAsyncClient.sendMessage(
                request -> request.messageBody(getMessageBodyAsJson(message))
                                    .queueUrl(queueUrl))
                .thenRun(() -> log.info("Sent message with uuid:{}",message.getUuid()));
    }

    private String getMessageBodyAsJson(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting payload: " + payload, e);
        }
    }
}
