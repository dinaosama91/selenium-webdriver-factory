package com.eventum.nms.core.configuration;

import com.eventumsolutions.nms.logging.ApplicationLogger;

public class DriverManagerFactory {

  public static DriverManager getManager(DriverType type) {
    DriverManager webdriver;
    switch (type) {
      case CHROME:
        webdriver = ChromeBrowser.getInstance();
        break;
      case FIREFOX:
        webdriver = FirefoxBrowser.getInstance();
        break;
      default:
        ApplicationLogger.logInfo("Unsupported driver type. Will start chrome !", null);
        webdriver = ChromeBrowser.getInstance();
        break;
    }
    return webdriver;
  }

  public static DriverManager getDriverManager(String driver) {
    return getManager(DriverType.valueOf(driver.toUpperCase()));
  }

}
