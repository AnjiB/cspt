package com.anji.sel.core;

import com.anji.sel.annotation.BrowserOptions;
import com.anji.sel.annotation.DefaultBrowserOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

  public static WebDriver getDriver(String browser, Class<? extends BrowserOptions> options) {
    return getDriverInstance(browser, options);
  }

  public static WebDriver getDriverInstance(
      String browser, Class<? extends BrowserOptions> options) {

    boolean isOptionsNotPresent = options == DefaultBrowserOptions.class;
    return switch (browser.toLowerCase()) {
      case "chrome" -> isOptionsNotPresent
          ? new ChromeDriver()
          : new ChromeDriver(getChromeOptions(options));
      case "firefox" -> isOptionsNotPresent
          ? new FirefoxDriver()
          : new FirefoxDriver(getFirefoxOptions(options));
      case "ie" -> new InternetExplorerDriver();
      case "safari" -> new SafariDriver();
      default -> throw new IllegalArgumentException("Unexpected value: " + browser);
    };
  }

  private static ChromeOptions getChromeOptions(Class<? extends BrowserOptions> optionsClass) {
    try {
      BrowserOptions options = optionsClass.getDeclaredConstructor().newInstance();
      ChromeOptions chromeOptions = new ChromeOptions();
      if (options.isHeadless()) {
        chromeOptions.addArguments("--headless");
      }
      if (options.getArguments() != null) {
        chromeOptions.addArguments(options.getArguments().split(","));
      }
      return chromeOptions;
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate BrowserOptions", e);
    }
  }

  private static FirefoxOptions getFirefoxOptions(Class<? extends BrowserOptions> optionsClass) {
    try {
      BrowserOptions options = optionsClass.getDeclaredConstructor().newInstance();
      FirefoxOptions firefoxOptions = new FirefoxOptions();
      if (options.isHeadless()) {
        firefoxOptions.addArguments("--headless");
      }
      if (options.getArguments() != null) {
        firefoxOptions.addArguments(options.getArguments().split(","));
      }
      return firefoxOptions;
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate BrowserOptions", e);
    }
  }
}
