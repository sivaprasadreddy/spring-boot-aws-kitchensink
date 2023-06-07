package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.ApplicationProperties;
import com.sivalabs.awsdemo.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecretsControllerTest extends AbstractIntegrationTest {

    @Autowired
    ApplicationProperties properties;

    @Test
    void shouldLoadConfigFromSecrets() {
        assertThat(properties.apiKey()).isEqualTo("secret12345");
    }
}