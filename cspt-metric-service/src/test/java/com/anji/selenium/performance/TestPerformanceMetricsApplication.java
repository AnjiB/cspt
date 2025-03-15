package com.anji.selenium.performance;

import org.springframework.boot.SpringApplication;

public class TestPerformanceMetricsApplication {

  public static void main(String[] args) {
    SpringApplication.from(PerformanceMetricsApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
