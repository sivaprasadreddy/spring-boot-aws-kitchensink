package com.sivalabs.awsdemo.domain;

import com.sivalabs.awsdemo.ApplicationProperties;
import com.sivalabs.awsdemo.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    private final ApplicationProperties properties;
    private final AmazonS3Service amazonS3Service;

    public void upload(String filename, InputStream inputStream) throws IOException {
        log.debug("Uploading image to S3");
        try {
            var bytes = IOUtils.toByteArray(inputStream);
            var byteArrayInputStream = new ByteArrayInputStream(bytes);
            amazonS3Service.upload(properties.bucketName(), filename, byteArrayInputStream);
        } catch (Exception e) {
            log.error("IException: ", e);
            throw new RuntimeException(e);
        }
    }
}
