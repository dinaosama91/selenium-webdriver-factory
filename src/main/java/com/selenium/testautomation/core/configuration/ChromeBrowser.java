package com.selenium.testautomation.core.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Platform;

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
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    }
	  }

	  @Override
	  protected void initiateLocalBrowser() {
	    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, ConfigurationFiles.getpropertyValue("chromeDriverPath"));
	    startBrowser(new RemoteWebDriver(driverService.get().getUrl(), BrowserProfiles.chromeProfile()));
	  }

	  @Override
	  protected boolean startDriverService() {
	    if (Platform.getCurrent()==Platform.LINUX) {
	      return true;
	    }
	    driverService.set(new ChromeDriverService.Builder().usingDriverExecutable(new File(ConfigurationFiles.getpropertyValue("chromeDriverPath")))
	        .usingAnyFreePort().build());
	   return initiateAndStartService(driverService.get());
	  }
	}

