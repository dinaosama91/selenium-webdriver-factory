package com.selenium.testautomation.core.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.selenium.testautomation.logging.ApplicationLogger;

public class ExecutionListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		ApplicationLogger.logInfo("Started method " + result.getName() + " execution", this.getClass());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodname = result.getName().toString().trim();
		if (methodname.startsWith("generate") && methodname.contains("Report")) {
			ApplicationLogger.logInfo("Test method  " + result.getName() + " has finished successfully", this.getClass());
		} else {
			ApplicationLogger.logInfo("Test method  " + result.getName() + " has finished successfully", this.getClass());
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ApplicationLogger.logError("Error " + result.getName() + " test has failed", this.getClass());
		String methodName = result.getName().toString().trim();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ApplicationLogger.logInfo("skipping Method " + result.getName(), this.getClass());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		ApplicationLogger.logInfo("Started test" + context.getName() + "Execution", this.getClass());
	}

	@Override
	public void onFinish(ITestContext context) {
		ApplicationLogger.logInfo("Finished test " + context.getName() + "Execution", this.getClass());
	}

}
