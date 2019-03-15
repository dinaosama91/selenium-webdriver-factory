package com.eventum.nms.core.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FirefoxBrowser extends DriverManager {

  private static FirefoxBrowser browser;

  private FirefoxBrowser() {

  }

  public static FirefoxBrowser getInstance() {
    if (browser == null) {
      browser = new FirefoxBrowser();
    }
    return browser;
  }

  @Override
  protected void initiateRemoteBrowser(String hubURL) {
    try {
      startBrowser(new RemoteWebDriver(new URL(hubURL), DesiredCapabilities.firefox().merge(BrowserProfiles.firefoxprofile())));
      ((RemoteWebDriver) driver.get()).getSessionId();
      ((RemoteWebDriver) driver.get()).setFileDetector(new LocalFileDetector()); 
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void initiateLocalBrowser() {
    ConfigurationFiles.loadProperties(ConfigurationFiles.Application_Configurations);
    System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, ConfigurationFiles.getpropertyValue("geckoDriverPath"));
    startBrowser(new FirefoxDriver(BrowserProfiles.chromeProfile()));
  }

  @Override
  protected boolean startDriverService() {
    ConfigurationFiles.loadProperties(ConfigurationFiles.Application_Configurations);
    if (Platform.getCurrent()==Platform.LINUX) {
      return true;
    }
    else
    driverService = new GeckoDriverService.Builder().usingDriverExecutable(new File(ConfigurationFiles.getpropertyValue("geckoDriverPath")))
        .usingAnyFreePort().build();
    return initiateAndStartService(driverService);
  }
}
