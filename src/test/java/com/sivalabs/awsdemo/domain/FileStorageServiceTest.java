package com.sivalabs.awsdemo.domain;

import com.sivalabs.awsdemo.ApplicationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class FileStorageServiceTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ApplicationProperties properties;

    @Test
    void shouldUpload() throws IOException {
        InputStream is = new ByteArrayInputStream("sivalabs".getBytes(StandardCharsets.UTF_8));
        fileStorageService.upload("first.txt", is);
    }
}