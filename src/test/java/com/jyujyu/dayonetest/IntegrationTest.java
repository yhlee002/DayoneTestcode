package com.jyujyu.dayonetest;

import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// @SpringBootTest : 테스트시에는 SpringContext를 실행시키지 않지만, 이 어노테이션을 붙이면 모든 빈을 스캔 및 등록해주게 된다.
// @Ignore : 이 클래스는 다른 테스트 클래스가 상속할 부모 클래스로, 부모 클래스는 테스트가 동작하지 않아도 되어서 이 어노테이션을 붙인다.
// @Transactional : 테스트가 모두 동작한 이후 rollback되어 DB에는 커밋되지 않아 테스트 실행 전 상태로 돌아가게 된다.
@SpringBootTest
@Ignore
@Transactional
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInitializer.class)
public class IntegrationTest {

    static DockerComposeContainer rdbms;

    static {
        rdbms = new DockerComposeContainer((new File("infra/test/docker-compose.yaml")))
            .withExposedService( // 이 구문 하나가 하나의 컨테이너를 띄움
                "local-db",
                3306,
                Wait.forLogMessage(".*ready for connections.*", 1) // Docker container가 잘 실행되었는지 확인하는 정규식
                .withStartupTimeout(Duration.ofSeconds(300)) // 300초 동안 위와 같은 로그가 나올 때까지 기다린다.
            )
            .withExposedService(
                "local-db-migrate",
                0, // port 없음
                Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
                    .withStartupTimeout(Duration.ofSeconds(300))
            );

        rdbms.start();
    }

    static class IntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Map<String, String> properties = new HashMap<>();

            var rdbmsHost = rdbms.getServiceHost("local-db", 3306);
            var rdbmsPort = rdbms.getServicePort("local-db", 3306);

            properties.put("spring.datasource.url", "jdbc:mysql://" + rdbmsHost + ":" + rdbmsPort + "/score");

            TestPropertyValues.of(properties)
                .applyTo(applicationContext);
        }
    }
}
