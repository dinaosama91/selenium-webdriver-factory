/*******************************************************************************
 *  Copyright 2019 Greyskies. All Rights Reserved.
 *******************************************************************************/
package base.configs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import core.Configuration.ConfigurationFiles;
import core.Configuration.DriverManager;
import core.Configuration.DriverManagerFactory;
import core.Configuration.DriverType;
import core.Configuration.Navigate;
import logging.ApplicationLogger;

public class Setup {
	
	private static DriverManager driverManager;
	private static Navigate navigate;
	
	private static DriverManager getDriverManager(String driver) {
		return DriverManagerFactory.getManager(DriverType.valueOf(driver.toUpperCase()));
	}
	
	public static void loadAppConfigfile() throws IOException {
		ConfigurationFiles.loadProperties("application");
	}
	
	public static void loadCustomerConfigfile(String app) throws IOException {
		ConfigurationFiles.loadCustomerProperties(app);
	}
	
	public static void setLoggerConfigurationFilePath() {
		ApplicationLogger.setConfigurationFilePath(ConfigurationFiles.getPropertyValue("log4jConfig"));
	}
	
	public static void openBrowser(String browser, String env , String hubUrl) throws MalformedURLException, UnknownHostException {
		driverManager = getDriverManager(browser);
		driverManager.getDriver(env , hubUrl);
		Assert.assertEquals(DriverManager.getCurrentBrowserName(), browser);
	}
	
	public static void getTargetApp(String app) {
		try {
			navigate = new Navigate();
			navigate.goToApp(app);
			if(navigate.checkAppStatus() == 404) {
				ApplicationLogger.logError(String.format("Application %s is down. Will quit its driver instance", app), null);
				TearDown.closeBrowser();
				throw new AssertionError();
			}
		} catch (WebDriverException e) {
			ApplicationLogger.logError("Reached an error page or page took too long to load. Attempting a reload", null);
			navigate.reloadPage();
		}
	}

}
