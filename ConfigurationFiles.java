package com.eventum.nms.core.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class ConfigurationFiles {

  public static final String Application_Configurations = "applicationProperties";
  public static final String Ericsson_Configurations = "ericssonProperties";
  public static final String Paco_Configurations = "pacoproperties";
  public static final String DSL_Configurations = "dslconfigurations";

  private static HashMap<String, String> files = new HashMap<String, String>();

  private static Properties props = null;
  private static InputStream input = null;
  
   static  {
    files.put("applicationProperties", "./src/main/resources/application.properties");
    files.put("ericssonProperties", "./src/main/resources/ericsson.properties");
    files.put("pacoproperties", "./src/main/resources/orangePaco.properties");
    files.put("dslconfigurations", "./src/main/resources/orangeDsl.properties");
  }

  public static void loadProperties(String FileName) {
    try {
      props = new Properties();
      input = new FileInputStream(files.get(FileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getpropertyValue(String property) {
    try {
      props.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return props.getProperty(property);
  }

}
