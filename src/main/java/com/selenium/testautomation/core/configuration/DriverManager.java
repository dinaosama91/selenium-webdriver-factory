package com.selenium.testautomation.core.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public abstract class DriverManager {

  protected static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
  protected static ThreadLocal<DriverService> driverService = new ThreadLocal<DriverService>();
  private static Eventhandler handler = new Eventhandler();
  private static EventFiringWebDriver eventDriver = null;
  public static SessionId sessionID;

  protected abstract void initiateRemoteBrowser(String hubURL);

  protected abstract void initiateLocalBrowser();

  protected abstract boolean startDriverService();

  protected boolean initiateAndStartService(DriverService service) {
    if (Platform.getCurrent() == Platform.LINUX) {
      return true;
    }
    try {
      service.start();
      return service.isRunning();
    } catch (IOException e) {
      ApplicationLogger.logError("Service didn't start", this.getClass());
      return false;
    }
  }

  public static void stopService() {
    if (driverService != null && driverService.get().isRunning()) {
      ApplicationLogger.logError("Stopping current driver service!", null);
      driverService.get().stop();
      driverService.remove();
    } else {
      ApplicationLogger.logError("No running service to stop", null);
    }
  }

  private void openBrowser(Environment env) {
    switch (env) {
      case LOCAL:
        initiateLocalBrowser();
        break;
      case REMOTE:
        initiateRemoteBrowser(ConfigurationFiles.getpropertyValue("remoteHubUrl"));
        break;
      case LOCALGRID:
        initiateRemoteBrowser(ConfigurationFiles.getpropertyValue("localhubUrl"));
        break;
      default:
        initiateLocalBrowser();
        break;
    }
  }

  private void createDriver(String environment) {
    openBrowser(Environment.valueOf(environment.toUpperCase()));
  }

  public static void quitDriver() {
    if (null != driver.get()) {
      ApplicationLogger.logInfo("Quitting current browser!", null);
      getDriverInstance().quit();
      driver.remove();
    }
  }

  public static String getCurrentBrowserName() {
    return driver.get().getCapabilities().getBrowserName();
  }

  public WebDriver getDriver(String environment) {

    if (Platform.getCurrent() != Platform.LINUX) {
      startDriverService();
    }
    ApplicationLogger.logInfo("Initiating Browser!", this.getClass());
    createDriver(environment);
    eventDriver = new EventFiringWebDriver(driver.get());
    eventDriver.register(handler);
    return eventDriver;
  }

  public static WebDriver getDriverInstance() {
    if (null == driver.get()) {
      ApplicationLogger.logError("No Driver was created", null);
      return null;
    } else {
      return eventDriver;
    }
  }

  protected void startBrowser(RemoteWebDriver webdriver) {
    driver.set(webdriver);
    sessionID = driver.get().getSessionId();
    driver.get().manage().timeouts().implicitlyWait(1600, TimeUnit.MILLISECONDS);
    driver.get().manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
  }

}
