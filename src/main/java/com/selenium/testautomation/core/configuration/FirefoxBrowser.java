package com.selenium.testautomation.core.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
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
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    }
	  }

	  @Override
	  protected void initiateLocalBrowser() {
	    System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, ConfigurationFiles.getpropertyValue("geckoDriverPath"));
	    startBrowser(new RemoteWebDriver(driverService.get().getUrl(), BrowserProfiles.firefoxprofile()));
	  }

	  @Override
	  protected boolean startDriverService() {

	    if (Platform.getCurrent()==Platform.LINUX) {
	      return true;
	    }
	    
	    driverService.set( new GeckoDriverService.Builder().usingDriverExecutable(new File(ConfigurationFiles.getpropertyValue("geckoDriverPath")))
	        .usingAnyFreePort().build());
	    return initiateAndStartService(driverService.get());
	  }
	}

