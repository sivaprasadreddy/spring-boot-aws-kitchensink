package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.ApplicationProperties;
import com.sivalabs.awsdemo.domain.SecretsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/secrets")
@RequiredArgsConstructor
public class SecretsController {
    private final SecretsService secretsService;
    private final ApplicationProperties properties;

    @GetMapping
    public ResponseEntity<Map<String, String>> getAll() {
        secretsService.logSecrets();
        Map<String, String> response = Map.of(
                "apiKey", properties.apiKey()
        );
        return ResponseEntity.ok(response);
    }
}
