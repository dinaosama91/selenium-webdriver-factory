package com.selenium.testautomation.core.configuration;

import com.selenium.testautomation.core.configuration.ConfigurationFiles;
import com.selenium.testautomation.core.configuration.Environment;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import core.listeners.Eventhandler;
import logging.ApplicationLogger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;



public abstract class DriverManager {

  protected static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
  protected static ThreadLocal<DriverService> driverService = new ThreadLocal<DriverService>();
  private static Eventhandler handler = new Eventhandler();
  public static SessionId sessionID;
  public static EventFiringWebDriver eventDriver = null;

  protected abstract void initiateRemoteBrowser(String hubURL) throws MalformedURLException, UnknownHostException;

  protected abstract void initiateLocalBrowser() throws UnknownHostException;

  protected abstract boolean startDriverService();

  protected boolean initiateAndStartService(DriverService service) {
    if(Platform.getCurrent() != Platform.LINUX) {
      return true;
    }
    try {
      service.start();
      return service.isRunning();
    } catch (IOException e) {
      ApplicationLogger.logError("Service Didn't Start", this.getClass());
      return false;
    }

  }

  public static void stopService() {
    if(driverService != null && driverService.get().isRunning()) {
      ApplicationLogger.logError("Stopping current driver service", null);
      driverService.get().stop();
      driverService.remove();
    } else {
      ApplicationLogger.logError("No running services to stop", null);
    }
  }


  private void openBrowser(Environment env , String remoteHubUrl) throws MalformedURLException, UnknownHostException {
    switch (env) {
      case LOCAL:
        initiateLocalBrowser();
        break;
      case REMOTE:
        initiateRemoteBrowser(remoteHubUrl);
        break;
      case LOCALGRID:
        initiateRemoteBrowser(ConfigurationFiles.getpropertyValue("localHubUrl"));
        break;
      default:
        initiateLocalBrowser();
        break;
    }

  }

  public void createDriver(String environment , String remoteHubUrl) throws MalformedURLException, UnknownHostException {
    openBrowser(Environment.valueOf(environment.toUpperCase()) , remoteHubUrl);
  }

  public static void quitDriver() {
    if(driver.get() != null) {
      ApplicationLogger.logInfo("Quitting current browser", null);
      getDriverInstance().quit();
      driver.remove();
    }
  }

  public static String getCurrentBrowserName() {
    return driver.get().getCapabilities().getBrowserName();
  }

  public WebDriver getDriver(String environment , String remoteHubUrl) throws MalformedURLException, UnknownHostException {
    if(Platform.getCurrent() != Platform.LINUX) {
      startDriverService();
    }
    createDriver(environment , remoteHubUrl);
    eventDriver = new EventFiringWebDriver(driver.get());
    eventDriver.register(handler);
    return eventDriver;
  }

  public static WebDriver getDriverInstance() {
    if(driver.get() == null) {
      ApplicationLogger.logError("No Driver was Created", null);
      return null;
    } else {
      return eventDriver;
    }
  }

  protected void startBrowser(RemoteWebDriver webDriver) {
    driver.set(webDriver);
    sessionID = driver.get().getSessionId();
    driver.get().manage().deleteAllCookies();
    driver.get().manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
    driver.get().manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
//		driver.get().manage().window().maximize();
    driver.get().manage().window().setPosition(new Point(0, 0));
    driver.get().manage().window().setSize(new Dimension(1364, 730));
    System.out.println("Height: " + driver.get().manage().window().getSize().getHeight());
    System.out.println("Width: " + driver.get().manage().window().getSize().getWidth());
  }

}
