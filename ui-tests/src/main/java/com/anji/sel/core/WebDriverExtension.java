package com.anji.sel.core;

import com.anji.sel.annotation.BrowserOptions;
import com.anji.sel.annotation.SeleniumWebDriver;
import io.qameta.allure.Attachment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class WebDriverExtension
    implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
  private static final ThreadLocal<WebDriver> THREAD_LOCAL_WEB_DRIVER = new ThreadLocal<>();

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    Optional<SeleniumWebDriver> annotation = getUseWebDriverAnnotation(context);
    if (annotation.isPresent()) {
      String browser = annotation.get().browser();
      Class<? extends BrowserOptions> browserOptionsClass = annotation.get().options();
      WebDriver driver = WebDriverFactory.getDriver(browser, browserOptionsClass);
      THREAD_LOCAL_WEB_DRIVER.set(driver);
    }
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    WebDriver driver = THREAD_LOCAL_WEB_DRIVER.get();
    if (driver != null) {
      if (context.getExecutionException().isPresent()) {
        takeScreenshot(driver, context.getDisplayName());
      }
      driver.quit();
      THREAD_LOCAL_WEB_DRIVER.remove();
    }
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return parameterContext.getParameter().getType() == WebDriver.class;
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return THREAD_LOCAL_WEB_DRIVER.get();
  }

  private synchronized void takeScreenshot(WebDriver driver, String testName) throws IOException {
    Path screenshotFolder = Paths.get(System.getProperty("user.dir")).resolve("./screenshots");
    if (Files.notExists(screenshotFolder)) {
      Files.createDirectories(screenshotFolder);
    }

    if (driver instanceof TakesScreenshot) {
      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File destination = new File(screenshotFolder.toFile(), testName + ".png");
      FileUtils.copyFile(screenshot, destination);
      attachScreenshotToAllure(screenshot, testName);
    }
  }

  @Attachment(value = "{testName} Screenshot", type = "image/png")
  public byte[] attachScreenshotToAllure(File screenshot, String testName) throws IOException {
    try (FileInputStream fis = new FileInputStream(screenshot)) {
      return fis.readAllBytes();
    }
  }

  private Optional<SeleniumWebDriver> getUseWebDriverAnnotation(ExtensionContext context) {
    return context
        .getTestMethod()
        .map(method -> method.getAnnotation(SeleniumWebDriver.class))
        .or(() -> context.getTestClass().map(cls -> cls.getAnnotation(SeleniumWebDriver.class)));
  }
}
