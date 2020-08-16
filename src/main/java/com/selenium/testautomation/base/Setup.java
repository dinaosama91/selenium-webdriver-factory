package com.selenium.testautomation.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.selenium.testautomation.core.configuration.DriverManager;
import com.selenium.testautomation.core.configuration.Navigate;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import com.selenium.testautomation.core.configuration.ConfigurationFiles;
import com.selenium.testautomation.core.configuration.DriverManager;
import com.selenium.testautomation.core.configuration.DriverManagerFactory;
import com.selenium.testautomation.core.configuration.DriverType;
import com.selenium.testautomation.core.configuration.Navigate;
import com.selenium.testautomation.logging.ApplicationLogger;

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
		ApplicationLogger.setConfigurationFilePath(ConfigurationFiles.getpropertyValue("log4jConfig"));
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
