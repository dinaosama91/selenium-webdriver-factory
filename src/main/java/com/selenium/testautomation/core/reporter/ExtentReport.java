package com.selenium.testautomation.core.reporter;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.selenium.testautomation.core.configuration.ConfigurationFiles;
import com.selenium.testautomation.core.configuration.DriverManager;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport implements IReporter {

  private static final String OUTPUT_FOLDER = "Output_Folder";
  private static final String FILE_NAME = "ReportFile_Name";
  private static final String DOCUMENT_TITLE = "Document_Title";
  private static final String REPORT_NAME = "Report_Name";
  private static ExtentReports extent;

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    init();
    for (ISuite suite : suites) {
      Map<String, ISuiteResult> result = suite.getResults();
      for (ISuiteResult r : result.values()) {
        ITestContext context = r.getTestContext();
        try {
          buildTestNodes(context.getFailedTests(), Status.FAIL);
          buildTestNodes(context.getSkippedTests(), Status.SKIP);
          buildTestNodes(context.getPassedTests(), Status.PASS);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    for (String s : Reporter.getOutput()) {
      extent.setTestRunnerOutput(s);
    }
    extent.flush();
  }

  private void init() {
 
    File reportDir = new File(ConfigurationFiles.getpropertyValue(OUTPUT_FOLDER));
    reportDir.mkdir();
    ExtentHtmlReporter htmlReporter =
        new ExtentHtmlReporter(ConfigurationFiles.getpropertyValue(OUTPUT_FOLDER) + File.separator + ConfigurationFiles.getpropertyValue(FILE_NAME));
    htmlReporter.config().setDocumentTitle(ConfigurationFiles.getpropertyValue(DOCUMENT_TITLE));
    htmlReporter.config().setReportName(ConfigurationFiles.getpropertyValue(REPORT_NAME));
    htmlReporter.config().setTheme(Theme.STANDARD);
    htmlReporter.config().setCSS(
        ".brand-logo.blue.darken-3 {background-color: #000000 !important;}.side-nav {  background: #000000;}.side-nav i {color: #fff;}.side-nav li.active {background-color: #930b0f;}.side-nav a:hover {background-color: #930b0f;}.default {background-color: #afcac5;}.danger {background-color: #d71a21;}.success {background-color: #a5ab81;}");
    htmlReporter.config().setJS(
        "var logoImg=document.createElement('img');logoImg.setAttribute('style','position:relative; top: 10px; left: 5px; width: 30px;'),logoImg.src='../../src/main/resources/eventumlogo.png';for(var img=document.getElementsByClassName('brand-logo'),i=0;i<img.length;i++)img[i].innerHTML='',img[i].appendChild(logoImg);");
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    extent.setSystemInfo("Test Environment", System.getProperty("os.name"));
    extent.setSystemInfo("Java Version", System.getProperty("java.version"));
  }

  private void buildTestNodes(IResultMap tests, Status status) throws IOException {
    ExtentTest test;
    if (tests.size() > 0) {
      for (ITestResult result : tests.getAllResults()) {
        test = extent.createTest(result.getMethod().getMethodName());
        for (String group : result.getMethod().getGroups())
          test.assignCategory(group);
        if (result.getParameters().length != 0) {
          String params = "";
          for (Object parameter : result.getParameters()) {
            params += "<br>" + parameter.toString();
          }
          test.log(status, "Test Method had the following parameters : " + params);
        }
        if (result.getStatus() == ITestResult.FAILURE) {
          test.log(status, "Test " + status.toString().toLowerCase() + "ed");
          test.addScreenCaptureFromPath(test.getModel().getName() + DriverManager.sessionID+".png");
        } else if (result.getMethod().getMethodName().startsWith("generate") && result.getMethod().getMethodName().contains("Report")) {
          test.addScreenCaptureFromPath(test.getModel().getName() + ".png");
          test.log(status, "Test " + status.toString().toLowerCase() + "ed");
        } else {
          test.log(status, "Test " + status.toString().toLowerCase() + "ed");
        }
        test.getModel().setStartTime(getTime(result.getStartMillis()));
        test.getModel().setEndTime(getTime(result.getEndMillis()));
      }
    }
  }

  private Date getTime(long millis) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    return calendar.getTime();
  }

}
