package com.sivalabs.awsdemo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT,
properties = {
    "spring.config.import=aws-secretsmanager:/spring/secret"
})
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AbstractIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15.3-alpine");

    static final LocalStackContainer localstack = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:2.1.0"))
            .withCopyFileToContainer(MountableFile.forHostPath("localstack/"), "/etc/localstack/init/ready.d/")
            .waitingFor(Wait.forLogMessage(".*LocalStack initialized successfully\n", 1))
            ;

    static {
        Startables.deepStart(postgres, localstack).join();

        System.setProperty("spring.cloud.aws.endpoint", localstack.getEndpoint().toString());
        System.setProperty("spring.cloud.aws.credentials.access-key", localstack.getAccessKey());
        System.setProperty("spring.cloud.aws.credentials.secret-key", localstack.getSecretKey());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.cloud.aws.endpoint", localstack::getEndpoint);
        //registry.add("spring.cloud.aws.credentials.access-key", localstack::getAccessKey);
        //registry.add("spring.cloud.aws.credentials.secret-key", localstack::getSecretKey);
        registry.add("spring.cloud.aws.region.static", localstack::getRegion);
        registry.add("spring.cloud.aws.s3.path-style-access-enabled", ()-> true);
    }
}
