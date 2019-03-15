package com.eventum.nms.core.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.eventum.nms.core.listeners.Eventhandler;
import com.eventumsolutions.nms.logging.ApplicationLogger;

public abstract class DriverManager {

  protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
  protected static DriverService driverService;
  private static Eventhandler handler = new Eventhandler();
  private static EventFiringWebDriver eventDriver = null;

  protected abstract void initiateRemoteBrowser(String hubURL);

  protected abstract void initiateLocalBrowser();

  protected abstract boolean startDriverService();

  protected boolean initiateAndStartService(DriverService service) {
        if (Platform.getCurrent()==Platform.LINUX) {
          return true;
        }
        ConfigurationFiles.loadProperties(ConfigurationFiles.Application_Configurations);
        driverService = service;
        try {
          driverService.start();
        } catch (IOException e) {
          e.printStackTrace();
          return false;
        }
        return true;
      
  }

  private void stopService(DriverService service) {
    service = driverService;
    if (null != service && service.isRunning())
      service.stop();
  }

  private void openBrowser(Environment env) {
    ConfigurationFiles.loadProperties(ConfigurationFiles.Application_Configurations);
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

  public void quitDriver() {
    if (null != driver.get()) {
      ApplicationLogger.logInfo("Quitting current browser!", this.getClass());
      stopService(driverService);
      getDriverInstance().quit();
      driver.remove();
    }
  }

  public WebDriver getDriver(String environment) {

      if (!driverService.isRunning()) {
       if (Platform.getCurrent()!=Platform.LINUX) {
      startDriverService();
      }
      ApplicationLogger.logInfo("Initiating Browser!", this.getClass());
      createDriver(environment);
      eventDriver = new EventFiringWebDriver(driver.get());
      eventDriver.register(handler);
      }
      else {
        ApplicationLogger.logError("No service started!", this.getClass());
      }
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
    driver.get().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    driver.get().manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
  }

}
