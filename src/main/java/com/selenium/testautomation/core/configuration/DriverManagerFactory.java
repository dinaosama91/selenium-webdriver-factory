package com.selenium.testautomation.core.configuration;

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
	        webdriver = ChromeBrowser.getInstance();
	        break;
	    }
	    return webdriver;
	  }
	}