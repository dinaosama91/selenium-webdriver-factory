package com.selenium.testautomation.logging;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public class ApplicationLogger {

	public static void setConfigurationFilePath(String path) {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		ctx.setConfigLocation(new File(path).toURI());
	}

	public static void logError(Throwable th, Class<?> clazz) {
		logError("", th, clazz);
	}

	public static void logError(String message, Class<?> clazz) {
		logError(message, null, clazz);
	}

	public static void logError(String message, Throwable th, Class<?> clazz) {
		StringWriter sw = new StringWriter();
		if (th != null) {
			th.printStackTrace(new PrintWriter(sw));
		}
		LogManager.getLogger(clazz)
				.error(message == null || message.isEmpty() ? sw.toString() : message + "\n" + sw.toString());
	}

	public static void logInfo(String message, Class<?> clazz) {
		LogManager.getLogger(clazz).info(message);
	}

	public static void logFatal(Throwable th, Class<?> clazz) {
		logFatal(null, th, clazz);
	}

	public static void logFatal(String message, Throwable th, Class<?> clazz) {
		StringWriter sw = new StringWriter();
		th.printStackTrace(new PrintWriter(sw));
		LogManager.getLogger(clazz)
				.fatal(message == null || message.isEmpty() ? sw.toString() : message + "\n" + sw.toString());
	}

	public static void logDebug(Object obj, Class<?> clazz) {
		LogManager.getLogger(clazz).debug(obj);
	}

}
