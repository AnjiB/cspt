package com.anji.ui;

import com.anji.sel.annotation.SeleniumWebDriver;
import com.anji.sel.page.HomePage;
import com.anji.sel.page.LoginPage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

@SeleniumWebDriver
class UISecurityTest {

  private static final String TITLE = "ParaBank | Welcome | Online Banking";

  private WebDriver driver;

  private LoginPage loginPage;

  private HomePage homePage;

  // register a user on https://parabank.parasoft.com/parabank/index.htm
  // and update username and password variables for demo

  private static final String USERNAME = "";

  private static final String PASSWORD = "";

  @BeforeEach
  void setup(WebDriver driver) {
    this.driver = driver;
    loginPage = new LoginPage(this.driver);
    loginPage.navigate();
  }

  @Test
  void sessionExpirationTest() {
    Assertions.assertThat(loginPage.getTitle()).isEqualTo(TITLE);
    loginPage.loginValidUser(USERNAME, PASSWORD);
    homePage = new HomePage(this.driver);
    Assertions.assertThat(homePage.getUsername()).contains(USERNAME);
    driver.manage().deleteCookie(driver.manage().getCookieNamed("JSESSIONID"));
    driver.navigate().refresh();
    loginPage.loginValidUser(USERNAME, PASSWORD);
  }

  @Test
  void cookieSecurityTest() {
    Assertions.assertThat(loginPage.getTitle()).isEqualTo(TITLE);
    loginPage.loginValidUser(USERNAME, PASSWORD);
    Cookie sessionCukki = driver.manage().getCookieNamed("JSESSIONID");
    Assertions.assertThat(sessionCukki.isHttpOnly()).isTrue();
    Assertions.assertThat(sessionCukki.isSecure()).isTrue();
  }

  @Test
  void xssAttackTest() {
    Assertions.assertThat(loginPage.getTitle()).isEqualTo(TITLE);
    loginPage.loginValidUser("<script>alert('XSS');</script>", PASSWORD);
    boolean isAlertPresent = false;
    try {
      driver.switchTo().alert();
      isAlertPresent = true;
    } catch (NoAlertPresentException e) {
      // do nothing
    }
    Assertions.assertThat(isAlertPresent).isFalse();
  }

  @Test
  void verifyPasswordIsMasked() {
    Assertions.assertThat(loginPage.getTitle()).isEqualTo(TITLE);
    Assertions.assertThat(loginPage.getPasswordWE().getAttribute("type")).isEqualTo("password");
  }

  @Test
  void sqlIjectionTest() {
    Assertions.assertThat(loginPage.getTitle()).isEqualTo(TITLE);
    loginPage.loginValidUser("admin", "OR 1=1");
    homePage = new HomePage(this.driver);
    Assertions.assertThat(homePage.getUsernameWE().isDisplayed()).isTrue();

    /*
     * WebElement searchBox = driver.findElement(By.name("search"));
     * searchBox.sendKeys("'; DROP TABLE users; --"); searchBox.submit();
     */

  }
}
