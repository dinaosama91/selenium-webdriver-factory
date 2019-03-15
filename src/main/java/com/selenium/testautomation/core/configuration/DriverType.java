package com.selenium.testautomation.core.configuration;

public enum DriverType {
	  CHROME("chrome"), FIREFOX("firefox"), IE("ie"), SAFARI("safari");

	  private String textvalue;

	  private DriverType(String text) {
	    textvalue = text;
	  }

	  public String toString() {
	    return String.valueOf(textvalue);
	  }

	}