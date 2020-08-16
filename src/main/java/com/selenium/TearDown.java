/*******************************************************************************
 *  Copyright 2019 Greyskies. All Rights Reserved.
 *******************************************************************************/
package base.configs;

import core.Configuration.DriverManager;

public class TearDown {

	  public static void stopService() {
	    DriverManager.stopService();
	  }
	  public static void closeBrowser() {
	    DriverManager.quitDriver();
	    
	  }

}
