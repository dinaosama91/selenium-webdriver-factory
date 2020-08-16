package com.selenium.testautomation.core.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.Platform;

public class ChromeBrowser extends DriverManager{

	private static ChromeBrowser browser;

	private ChromeBrowser() {
		// TODO Auto-generated constructor stub
	}

	public static ChromeBrowser getInstance() {
		if(browser == null) {
			browser = new ChromeBrowser();
		}
		return browser;
	}

	@Override
	protected void initiateRemoteBrowser(String hubURL) throws MalformedURLException, UnknownHostException {
		startBrowser(new RemoteWebDriver(new URL(hubURL), DesiredCapabilities.chrome().merge(BrowserProfiles.chromeProfile())));
	}

	@Override
	protected void initiateLocalBrowser() throws UnknownHostException {
		WebDriverManager.chromedriver().setup();
		startBrowser(new ChromeDriver(BrowserProfiles.chromeProfile()));
	}

	@Override
	protected boolean startDriverService() {
		if(Platform.getCurrent() == Platform.LINUX) {
			return true;
		}
		WebDriverManager.chromedriver().setup();
		return initiateAndStartService(driverService.get());
	}

}
