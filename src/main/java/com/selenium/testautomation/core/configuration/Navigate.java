package com.selenium.testautomation.core.configuration;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

public class Navigate {
	private String request = "";

	  public void goToApp(String app) {
	    request = ConfigurationFiles.getpropertyValue("url");
	    if (request == null) {
	      ApplicationLogger.logError("Unsupported Application", this.getClass());
	    } else
	      DriverManager.getDriverInstance().get(request);
	  }

	  public void reloadPage() {
	    DriverManager.getDriverInstance().navigate().refresh();
	  }

	  public int checkAppStatus() {
	    try {
	      HttpUriRequest httprequest = new HttpGet(request);
	      CloseableHttpResponse urlresp = HttpClientBuilder.create().build().execute(httprequest);
	      return urlresp.getStatusLine().getStatusCode();
	    } catch (IOException e) {
	      ApplicationLogger.logError("Couldn't get response from application", this.getClass());
	      return 0;
	    }
	  }
}
