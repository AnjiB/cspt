package com.anji.sel.page;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class HomePage extends BasePage {

  private By usernameBy = By.xpath("//*[@id='leftPanel']/p");

  public HomePage(WebDriver driver) {
    super(driver);
  }

  public String getUsername() {

    FluentWait<WebDriver> wait =
        new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameBy));
    return element.getText();
  }

  public WebElement getUsernameWE() {
    return this.driver.findElement(usernameBy);
  }
}
