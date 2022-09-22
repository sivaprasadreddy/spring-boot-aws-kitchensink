package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.common.AbstractIntegrationTest;
import com.sivalabs.awsdemo.domain.Message;
import com.sivalabs.awsdemo.domain.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageControllerIT extends AbstractIntegrationTest {

    @Autowired
    MessageService messageService;

    @Test
    void shouldSendMessageSuccessfully() throws Exception {
        Message msg = new Message(null, UUID.randomUUID().toString(), "test-message");
        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(msg)))
                .andExpect(status().isOk());

        await().atMost(5, SECONDS).until(() -> {
            Optional<Message> message = messageService.findByUuid(msg.getUuid());
            return message.isPresent();
        });

    }
}