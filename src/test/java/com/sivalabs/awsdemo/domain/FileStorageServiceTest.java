package com.sivalabs.awsdemo.domain;

import com.sivalabs.awsdemo.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class FileStorageServiceTest extends AbstractIntegrationTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    void shouldUpload() throws IOException {
        InputStream is = new ByteArrayInputStream("sivalabs".getBytes(StandardCharsets.UTF_8));
        String fileName = "first1.txt";
        fileStorageService.upload(fileName, is);

        byte[] bytes = fileStorageService.download(fileName);
        String content = new String(bytes);

        assertThat(content).isEqualTo("sivalabs");
    }
}