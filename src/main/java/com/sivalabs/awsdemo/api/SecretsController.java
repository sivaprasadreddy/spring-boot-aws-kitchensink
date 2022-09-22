package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.domain.SecretsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secrets")
@RequiredArgsConstructor
public class SecretsController {
    private final SecretsService secretsService;

    @GetMapping
    public void getAll() {
        secretsService.logSecrets();
    }
}
