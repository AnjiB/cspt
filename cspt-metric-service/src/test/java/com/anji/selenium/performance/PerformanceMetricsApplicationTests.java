package com.anji.selenium.performance;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class PerformanceMetricsApplicationTests {

  @Configuration
  @EnableAutoConfiguration
  public static class TestConfig {}

  @Test
  void contextLoads() {}
}
