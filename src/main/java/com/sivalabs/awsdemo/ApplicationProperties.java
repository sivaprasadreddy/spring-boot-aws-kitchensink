package com.sivalabs.awsdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(
        String bucketName,
        String queueName,
        String topicName,
        String apiKey
) {}
