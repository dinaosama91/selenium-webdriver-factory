package com.selenium.testautomation.base;

import com.selenium.testautomation.core.configuration.DriverManager;
import com.selenium.testautomation.core.configuration.DriverManager;

public class TearDown {

	  public static void stopService() {
	    DriverManager.stopService();
	  }
	  public static void closeBrowser() {
	    DriverManager.quitDriver();
	    
	  }

}
