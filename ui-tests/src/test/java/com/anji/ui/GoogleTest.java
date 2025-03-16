package com.anji.ui;

import com.anji.sel.annotation.SeleniumWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

@SeleniumWebDriver
@Slf4j
class GoogleTest {

  private WebDriver driver;

  @BeforeEach
  void setup(WebDriver driver) {
    this.driver = driver;
  }

  @Test
  void testGooglePageTitle() {
    log.info(Thread.currentThread().getName());
    driver.get("https://www.google.com");
    Assertions.assertThat(driver.getTitle()).isEqualTo("Facebook");
  }

  @Test
  void testFacebookPageTitle() {
    log.info(Thread.currentThread().getName());
    driver.get("https://www.facebook.com");
    Assertions.assertThat(driver.getTitle()).isEqualTo("Google");
  }
}
