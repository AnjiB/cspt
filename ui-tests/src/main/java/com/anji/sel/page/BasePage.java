package com.anji.sel.page;

import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {

  protected WebDriver driver;

  public BasePage(final WebDriver driver) {
    this.driver = driver;
  }

  public String getCurrentUrl() {
    return this.driver.getCurrentUrl();
  }

  public String getTitle() {
    return this.driver.getTitle();
  }

  public Set<Cookie> getCookies() {
    return this.driver.manage().getCookies();
  }

  public void addCookie(Cookie kukki) {
    this.driver.manage().addCookie(kukki);
  }
}
