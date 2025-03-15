package com.anji.ui;

import com.anji.sel.PerformanceClient;
import com.anji.sel.annotation.SeleniumWebDriver;
import com.anji.sel.pojo.metric.MetricDto;
import com.anji.sel.util.FilterUtil;
import com.anji.sel.util.TransformUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v133.performance.Performance;
import org.openqa.selenium.devtools.v133.performance.model.Metric;

@SeleniumWebDriver
public class UIPerformanceTest {

  private static final String HOST_BASE_URL = "https://automationteststore.com/";

  private PerformanceClient performanceClient;

  private WebDriver driver;

  private List<Metric> metricList;

  @BeforeEach
  void setup(WebDriver driver) {
    this.driver = driver;
    performanceClient = new PerformanceClient();
  }

  @Test
  void networkTest() {
    try (DevTools devTools = ((ChromeDriver) driver).getDevTools()) {
      devTools.createSession();
      devTools.send(Performance.enable(Optional.empty()));
      driver.get(HOST_BASE_URL);
      metricList = devTools.send(Performance.getMetrics());
    }
  }

  @AfterEach
  void flushMetrics() {
    List<MetricDto> metricDtoList = TransformUtil.transform(HOST_BASE_URL, metricList);
    performanceClient.sendMetrics(FilterUtil.filter(metricDtoList));
  }
}
