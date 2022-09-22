package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.domain.Message;
import com.sivalabs.awsdemo.domain.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public void send(@RequestBody Message message) {
        if(Objects.isNull(message.getUuid())) {
            message.setUuid(UUID.randomUUID().toString());
        }
        messageService.send(message).join();
    }
}
