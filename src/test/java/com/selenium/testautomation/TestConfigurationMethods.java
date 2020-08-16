/*******************************************************************************
 *  Copyright 2019 Greyskies. All Rights Reserved.
 *******************************************************************************/
package com.selenium.testautomation;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.selenium.testautomation.base.Setup;
import com.selenium.testautomation.base.TearDown;
import com.selenium.testautomation.core.configuration.DriverManager;

public class TestConfigurationMethods {
	
	@BeforeSuite
	public void configureLogger() throws IOException {
		Setup.loadAppConfigfile();
		Setup.setLoggerConfigurationFilePath();
	}
	

	@Parameters ({ "browser","env" , "targetUrl", "hubUrl" })
	@BeforeTest
	public void setupTestEnvironment(String browser, String env, String targetApp, String hubUrl) throws IOException {
		Setup.openBrowser(browser, env , hubUrl);
		Setup.getTargetApp(targetApp);
	}

	
	@AfterTest(enabled=true)
	 public void tearDownEnvironment () {
	      TearDown.closeBrowser();
	      Runtime.getRuntime().gc();
	 }
	
	@AfterTest(enabled=true)
	 public void cleanDriver () {
	      DriverManager.quitDriver();
	 }
}
