package com.selenium.testautomation.core.configuration;

public enum Environment {

	  LOCAL("local"), REMOTE("remote"), LOCALGRID("local/grid");

	  private String text;

	  private Environment(String text) {
	    this.text = text;
	  }

	  public String toString() {
	    return String.valueOf(text);
	  }

	}
