package com.sivalabs.awsdemo.domain;

import com.sivalabs.awsdemo.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecretsService {
    private final SecretsManagerClient secretsManagerClient;
    private final ApplicationProperties properties;

    public void logSecrets() {
        log.info("apiKey:{}", properties.apiKey());
        secretsManagerClient.listSecrets().secretList().forEach(secretEntry -> {
            log.debug("secret name: {}", secretEntry.name());
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                    .secretId(secretEntry.name())
                    .build();

            GetSecretValueResponse valueResponse = secretsManagerClient.getSecretValue(valueRequest);
            String secret = valueResponse.secretString();
            log.debug("secret value: {}", secret);
        });
    }
}
