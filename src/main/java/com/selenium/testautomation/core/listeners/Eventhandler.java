package com.selenium.testautomation.core.listeners;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.testng.Assert;

import com.selenium.testautomation.core.configuration.DriverManager;
import com.selenium.testautomation.logging.ApplicationLogger;


public class Eventhandler extends AbstractWebDriverEventListener {

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {

	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {

	}

	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {
		ApplicationLogger.logDebug("About to reload Web Page", this.getClass());
	}

}
