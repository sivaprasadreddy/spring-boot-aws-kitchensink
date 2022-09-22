package com.sivalabs.awsdemo.common;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SECRETSMANAGER;

public class LocalstackContainerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    static final DockerImageName imageName = DockerImageName.parse("localstack/localstack:1.1.0");
    static final LocalStackContainer localstack = new LocalStackContainer(imageName)
            .withServices(
                    LocalStackContainer.Service.S3,
                    LocalStackContainer.Service.SQS,
                    LocalStackContainer.Service.SNS,
                    SECRETSMANAGER
            )
            .withCopyFileToContainer(MountableFile.forHostPath("localstack/"), "/docker-entrypoint-initaws.d/");

    static {
        localstack.start();
        System.setProperty("spring.config.import","optional:aws-secretsmanager:/secrets/api-secrets");
    }

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        String awsEndpoint = "http://" + localstack.getHost() + ":" + localstack.getMappedPort(4566);
        TestPropertyValues.of(
                "spring.cloud.aws.endpoint=" + awsEndpoint,
                "spring.cloud.aws.s3.path-style-access-enabled=true"

                // **** Following settings not working ***
                // https://github.com/awspring/spring-cloud-aws/issues/518

                //"spring.cloud.aws.secretsmanager.endpoint=" + localstack.getEndpointOverride(SECRETSMANAGER).toString(),
                //"spring.cloud.aws.secretsmanager.region="+localstack.getRegion(),
                //"spring.config.import=optional:aws-secretsmanager:/secrets/api-secrets"
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}