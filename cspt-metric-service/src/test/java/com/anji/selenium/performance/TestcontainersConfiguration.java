package com.anji.selenium.performance;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

  private static final String MYSQL_IMAGE = "mysql:8.0";
  private static final String DATABASE_NAME = "test_selenium";
  private static final String USERNAME = "test";
  private static final String PASSWORD = "test";

  @Bean
  @ServiceConnection
  public MySQLContainer<?> mysqlContainer() {
    return new MySQLContainer<>(DockerImageName.parse(MYSQL_IMAGE))
        .withDatabaseName(DATABASE_NAME)
        .withUsername(USERNAME)
        .withPassword(PASSWORD)
        .withReuse(true);
  }
}
