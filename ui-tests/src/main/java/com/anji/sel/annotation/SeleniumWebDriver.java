package com.anji.sel.annotation;

import com.anji.sel.core.WebDriverExtension;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(WebDriverExtension.class)
public @interface SeleniumWebDriver {

  String browser() default "chrome";

  Class<? extends BrowserOptions> options() default DefaultBrowserOptions.class;
}
