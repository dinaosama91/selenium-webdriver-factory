package com.eventum.nms.core.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromeBrowser extends DriverManager {

  private static ChromeBrowser browser;

  private ChromeBrowser() {

  }

  public static ChromeBrowser getInstance() {
    if (browser == null) {
      browser = new ChromeBrowser();
    }
    return browser;
  }

  @Override
  protected void initiateRemoteBrowser(String hubURL) {
    try {
      startBrowser(new RemoteWebDriver(new URL(hubURL), DesiredCapabilities.chrome().merge(BrowserProfiles.chromeProfile())));
      ((RemoteWebDriver) driver.get()).getSessionId();
      ((RemoteWebDriver) driver.get()).setFileDetector(new LocalFileDetector()); 
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void initiateLocalBrowser() {
    ConfigurationFiles.loadProperties(ConfigurationFiles.Application_Configurations);
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, ConfigurationFiles.getpropertyValue("chromeDriverPath"));
    startBrowser(new ChromeDriver(BrowserProfiles.chromeProfile()));
  }

  @Override
  protected boolean startDriverService() {
    ConfigurationFiles.loadProperties(ConfigurationFiles.Application_Configurations);
    if (Platform.getCurrent()==Platform.LINUX) {
      return true;
    } else
    driverService = new ChromeDriverService.Builder().usingDriverExecutable(new File(ConfigurationFiles.getpropertyValue("chromeDriverPath")))
        .usingAnyFreePort().build();
    boolean service = initiateAndStartService(driverService);
    return service;
  }
}
