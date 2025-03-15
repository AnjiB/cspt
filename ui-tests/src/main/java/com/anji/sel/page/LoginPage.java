package com.anji.sel.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

  private By usernameBy = By.name("username");

  private By passwordBy = By.name("password");

  private By loginBy = By.cssSelector("input[value='Log In']");

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  public void navigate() {
    this.driver.get("https://parabank.parasoft.com/parabank/index.htm");
  }

  public String getTitle() {
    return this.driver.getTitle();
  }

  public void loginValidUser(String userName, String password) {

    this.driver.findElement(usernameBy).sendKeys(userName);
    this.getPasswordWE().sendKeys(password);
    this.driver.findElement(loginBy).click();
  }

  public WebElement getPasswordWE() {
    return this.driver.findElement(passwordBy);
  }
}
