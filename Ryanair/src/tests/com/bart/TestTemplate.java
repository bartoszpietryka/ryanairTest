package com.bart;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bart.exceptions.PropertiesKeeperException;
import com.bart.exceptions.RyanairTestCommonException;
import com.bart.utils.PageTemplate;
import com.bart.utils.PropertiesKeeper;
import com.bart.utils.WebDriverKeeper;
import com.google.common.base.Function;

/**
 * All tests classes will inherit from this class. We put here methods that will
 * be used in many tests
 * 
 * @author bart
 *
 */
public class TestTemplate extends PageTemplate {
	private final static Logger log = Logger.getLogger(TestTemplate.class.getName());

	//We don't expect any exceptions by default
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Rule
	public TestName testName = new TestName();

	/**
	 * Executed before any test starts
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpTestClass() throws Exception {
		log.info("before script class");

	}

	/**
	 * This is executed before each test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUpTest() throws Exception {
		try {
			log.info("Before test: " + testName.getMethodName() + "   in class: " + this.getClass().getSimpleName());

		} catch (Exception e1) {
			log.error("Test failed in setUpTest");
			log.error(e1.getMessage(), e1);
			throw e1;
		}
	}

	/**
	 * Executed after all tests finish
	 * @throws RyanairTestCommonException 
	 * 
	 */
	@AfterClass
	public static void terminateTests() throws RyanairTestCommonException {
		log.info("After script class");
		try {
			if (PropertiesKeeper.getInstance().getProp("KeepBrowser") != null
					&& !PropertiesKeeper.getInstance().getProp("KeepBrowser").equalsIgnoreCase("TRUE"))
				WebDriverKeeper.getInstance().quit();
		} catch (PropertiesKeeperException e) {
			log.error("Unable to use property manager");
			e.printStackTrace();
		}
	}

	/***
	 * This is executed after each test. Even when test fails, like i.e.
	 * terminates with exception
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		log.info("After test " + testName.getMethodName() + "   in class: " + this.getClass().getSimpleName());
	}


}
