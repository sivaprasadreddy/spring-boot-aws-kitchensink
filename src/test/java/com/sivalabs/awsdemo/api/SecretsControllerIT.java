package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecretsControllerIT extends AbstractIntegrationTest {
    @Test
    void shouldLogSecrets() throws Exception {
        mockMvc.perform(get("/api/secrets"))
                .andExpect(status().isOk());
    }
}