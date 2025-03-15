package com.anji.sel.annotation;

public class CustomBrowserOptions extends BrowserOptions {
  public CustomBrowserOptions() {
    super(false, "--remote-debugging-port=9222");
  }
}
