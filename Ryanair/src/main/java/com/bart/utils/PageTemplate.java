package com.bart.utils;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bart.exceptions.PropertiesKeeperException;
import com.bart.exceptions.RyanairTestCommonException;
import com.google.common.base.Function;

/**
 * All pages and tests classes will inherit from this class. We put here methods
 * that will be commonly used in project. It also contains rules for jUnit.
 * Because pages classes can also contain unit test, it's better to put them here
 * instead of TestTemplate.java
 * 
 * @author bart
 *
 */
public class PageTemplate {
	private final static Logger log = Logger.getLogger(PageTemplate.class.getName());

	/**
	 * Retrieves from stacktrace name of calling method
	 * 
	 * @return MethodName
	 */
	public static String getMethodName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];
		return e.getMethodName();
	}

	public void waitForPageLoad() throws RyanairTestCommonException {

		Wait<WebDriver> wait = new WebDriverWait(WebDriverKeeper.getInstance().getWebDriver(),
				PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}
	
	/**
	 * We want to have information in log file about test failure. Even if whole
	 * jUnit test ends with exception
	 */
	@Rule
	public TestRule watchman = new TestWatcher() {

		@Override
		protected void starting(Description description) {
			log.info("Starting test. info: " + description);
		}

		@Override
		protected void failed(Throwable e, Description description) {
			log.error("Failed test. info: \n" + description + "\n" + e.getMessage(), e);
		}

		@Override
		protected void succeeded(Description description) {
			log.info("Succeeded test. info: " + description);
		}

		@Override
		protected void finished(Description description) {
			log.info("Finished test. info: " + description);
		}

	};

}
