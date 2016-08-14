package com.bart.test.testSuites;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bart.test.SimpleLogin;

/**
 * Test suites are very comfortable way to organize tests. Ecpilse jUnit plugin
 * allows to easily review failed tests and test classes.
 * In this  project we have only one test class so test suite is basically useless
 * 
 * @author bart
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ SimpleLogin.class, })
public class TestSuite1 {
	private final static Logger log = Logger.getLogger(TestSuite1.class.getName());

	/**
	 * Executed before test suite
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpTestSuite() throws Exception {
		log.info("Before suite class");
	}

	/**
	 * Executed after all tests in suite finish
	 * 
	 */
	@AfterClass
	public static void terminateTestSuite() {
		log.info("After suite class");
	}

}
