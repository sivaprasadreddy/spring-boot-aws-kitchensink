package com.sivalabs.awsdemo.domain;

import com.sivalabs.awsdemo.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    private final ApplicationProperties properties;
    private final S3Client s3Client;

    public void upload(String filename, InputStream inputStream) throws IOException {
        log.debug("Uploading image to S3");
        try {
            var bytes = IOUtils.toByteArray(inputStream);
            var byteArrayInputStream = new ByteArrayInputStream(bytes);
            this.upload(properties.bucketName(), filename, byteArrayInputStream);
        } catch (Exception e) {
            log.error("IException: ", e);
            throw new RuntimeException(e);
        }
    }

    private void upload(String bucketName, String key, InputStream inputStream) throws IOException {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(objectRequest, RequestBody.fromBytes(inputStream.readAllBytes()));
    }
}
