package com.anji.ui;

import com.anji.sel.annotation.SeleniumWebDriver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v133.network.Network;

@SeleniumWebDriver
public class SecureHeadersTest {

  private WebDriver driver;

  @BeforeEach
  void setup(WebDriver driver) {
    this.driver = driver;
  }

  @Test
  void networkTest() throws InterruptedException {
    try (DevTools devTools = ((ChromeDriver) driver).getDevTools()) {
      devTools.createSession();
      devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
      List<SoftAssertions> assertionsList = new ArrayList<>();
      CompletableFuture<Void> headersProcessed = new CompletableFuture<>();
      devTools.addListener(
          Network.responseReceived(),
          response -> {
            Map<String, Object> headers = new HashMap<>(response.getResponse().getHeaders());
            SoftAssertions softAssertions = new SoftAssertions();
            String urlMessage = "Header missing for URL:" + response.getResponse().getUrl();
            softAssertions
                .assertThat(headers.containsKey("Strict-Transport-Security"))
                .as(urlMessage)
                .isTrue();
            softAssertions
                .assertThat(headers.containsKey("Content-Security-Policy"))
                .as(urlMessage)
                .isTrue();
            softAssertions
                .assertThat(headers.containsKey("X-XSS-Protection"))
                .as(urlMessage)
                .isTrue();
            softAssertions
                .assertThat(headers.containsKey("X-Frame-Options"))
                .as(urlMessage)
                .isTrue();
            synchronized (assertionsList) {
              assertionsList.add(softAssertions);
            }
            headersProcessed.complete(null);
          });

      driver.get("https://automationexercise.com/");

      headersProcessed.join(); //
      assertionsList.forEach(softly -> softly.assertAll());
    }
  }
}
