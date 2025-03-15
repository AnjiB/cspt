package com.anji.ui;

import com.anji.sel.PerformanceClient;
import com.anji.sel.annotation.CustomBrowserOptions;
import com.anji.sel.annotation.SeleniumWebDriver;
import com.anji.sel.light.LightHouse;
import com.anji.sel.pojo.metric.MetricDto;
import com.anji.sel.util.FilterUtil;
import com.anji.sel.util.TransformUtil;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

@SeleniumWebDriver(options = CustomBrowserOptions.class)
@Slf4j
public class LightHouseMetricsTest {

  private static final String HOST_BASE_URL = "https://automationteststore.com/";

  private PerformanceClient performanceClient;

  private WebDriver driver;

  @BeforeEach
  void setup(WebDriver driver) {
    this.driver = driver;
    performanceClient = new PerformanceClient();
  }

  @Test
  void lightHouseTest() throws Exception {
    this.driver.get(HOST_BASE_URL);
    LightHouse.analyse(HOST_BASE_URL, "desktop");
    Thread.sleep(10000);
  }

  @AfterEach
  void flushMetrics() throws IOException {
    List<MetricDto> metricDtoList = TransformUtil.transformFromLighthouse();
    log.info(metricDtoList.toString());
    performanceClient.sendMetrics(FilterUtil.filter(metricDtoList));
  }
}
