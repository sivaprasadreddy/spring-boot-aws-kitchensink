package com.sivalabs.awsdemo.domain;

import com.sivalabs.awsdemo.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class FileStorageServiceTest extends AbstractIntegrationTest {
    @Autowired
    private FileStorageService fileStorageService;

    @Test
    void shouldUpload() throws IOException {
        InputStream is = new ByteArrayInputStream("sivalabs".getBytes(StandardCharsets.UTF_8));
        fileStorageService.upload("first1.txt", is);
    }
}