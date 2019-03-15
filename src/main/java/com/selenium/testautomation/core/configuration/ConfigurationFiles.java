package com.selenium.testautomation.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationFiles {

	  private static String FILE_PATH = "./src/main/resources";
	  private static Properties props = null;
	  private static Properties customer = null;
	  private static InputStream input = null;

	  private static String getPropertyFilename(String file) {
	    return file + ".properties";
	  }

	  public static void loadProperties(String FileName) {
	    if (props == null) {
	      try {
	        props = new Properties();
	        FileName = getPropertyFilename(FileName);
	        input = new FileInputStream(FILE_PATH + File.separator + FileName);
	        props.load(input);
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	  }

	  public static void loadCustomerProperties(String FileName) {
	    try {
	      customer = new Properties();
	      FileName = getPropertyFilename(FileName);
	      input = new FileInputStream(FILE_PATH + File.separator + FileName);
	      customer.load(input);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	  }

	  public static String getpropertyValue(String property) {
	    if (props.getProperty(property) == null) {
	      return customer.getProperty(property);
	    } else
	      return props.getProperty(property);
	  }

	}

