package com.bart.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bart.exceptions.NoSuchDriverException;
import com.bart.exceptions.PropertiesKeeperException;
import com.bart.exceptions.RyanairTestCommonException;

/**
 * When FirefoxDriver (or drivers for other browsers) is initialized, new
 * browser windows is created in separate process. Furthermore, with standard
 * settings ,new profile is created each time. It consumes lots of resources.
 * That's why instance is usually kept in singleton, static class or created in
 * jUnit @BeforeClass Other methods that allow to setup browser (i.e. cookie
 * handling) are also usually kept in class like this one.
 * 
 * @author bart
 *
 */
public class WebDriverKeeper {
	private final static Logger log = Logger.getLogger(WebDriverKeeper.class.getName());
	private static WebDriverKeeper instance = null;
	private WebDriver webDriver = null;

	/**
	 * Singleton constructor. Never used
	 */
	private WebDriverKeeper() {
		
	}
	/**
	 * Singleton constructor
	 * @throws NoSuchDriverException 
	 * @throws PropertiesKeeperException 
	 */
	private WebDriverKeeper(String browser) throws NoSuchDriverException, PropertiesKeeperException {
		try{
		log.info("Starting browser: " + browser);
		if (browser.equalsIgnoreCase("firefox")) 
			webDriver = new FirefoxDriver();
		else if (browser.equalsIgnoreCase("chrome")) {		
			System.setProperty("webdriver.chrome.driver",PropertiesKeeper.getInstance().getProp("ChromeDriverPath") );
			webDriver = new ChromeDriver();
		}
		else
			throw new NoSuchDriverException("Driver " + browser + " is not supported");
		try {
			// changing default timeout
			webDriver.manage().timeouts().implicitlyWait(PropertiesKeeper.getInstance().getPropAsInt("ShortWait"),
					TimeUnit.SECONDS);
		} catch (PropertiesKeeperException e) {
			log.error("PropertiesKeeper cold not read \"ShortWait\" property. Using default values");
			log.error(e.getMessage());
		}
		} catch (Exception e){
			log.error("Loading of WebDriver Failed:" + e.getMessage());
			
		}
	}

	/**
	 * This method returns instance of WebDriverKeeper class. If instance does
	 * not exit yet, it's automatically initialized.
	 * 
	 * @return Instance of WebDriverKeeper class
	 * @throws RyanairTestCommonException 
	 */
	public static WebDriverKeeper getInstance() throws RyanairTestCommonException {
		if (instance == null) {
			log.warn("Driver not specified. Using default firefox");
			instance = new WebDriverKeeper("Firefox");
		}
		return instance;
	}

	/**
	 * This method returns instance of WebDriverKeeper class. If instance does
	 * not exit yet, it's automatically initialized.
	 * 
	 * @return Instance of WebDriverKeeper class
	 * @throws RyanairTestCommonException 
	 */
	public static WebDriverKeeper setBrowser(String browser) throws RyanairTestCommonException {
		if (instance!=null)
			 getInstance().quit();
	    instance = new WebDriverKeeper(browser);	
			
		return instance;
	}
	/**
	 * Calls <code>WebDriver.quit()</code> and sets stored WebDriver to null.
	 * 
	 */
	public void quit() {
		log.info("Closing browser");
		if (webDriver != null) {
			webDriver.quit();
			log.info("Window closed");
			webDriver = null;
		}
		// not exaclly singleton like
		instance = null;
	}

	/**
	 * This method returns reference to org.openqa.selenium.WebDriver interface
	 * object, that is hold in this singleton.
	 * 
	 * @return Direct access to currently used webDriver
	 * @throws RyanairTestCommonException 
	 */
	public WebDriver getWebDriver() throws RyanairTestCommonException {
		return getInstance().webDriver;
	}

	/**
	 * Navigates to webpage with URL <code>address</code>
	 * 
	 * @param address
	 *            URL address in <code>http://domain/path</code> format
	 */
	public void goToPage(String address) {
		log.info("Navigating to URL: " + address);
		webDriver.get(address);
		webDriver.switchTo().defaultContent();
	}

	/**
	 * Synonym for getWebDriver().findElement(By.xpath( xpathExpression))
	 * 
	 * @param xpathExpression
	 *            Xpath of element to find
	 * @return Found WebElement
	 * @throws NoSuchElementException
	 *             If element was not found in current WebDrive context
	 */
	public WebElement elementByXPath(String xpathExpression) throws NoSuchElementException {

		return webDriver.findElement(By.xpath(xpathExpression));
	}
	
	/**
	 * Synonym for getWebDriver().findElements(By.xpath(
	 * xpathExpression))
	 * 
	 * @param xpathExpression
	 *            Xpath of elements to find
	 * @return Found WebElement
	 * @throws NoSuchElementException
	 *             If element was not found in current WebDrive context
	 */
	public List<WebElement> elementsByXPath(String xpathExpression)
			throws NoSuchElementException {

		return webDriver.findElements(By.xpath(xpathExpression));
	}
	
	/**
	 * This method will wait for WebElement with XPath <code>xPath</code> for
	 * <code>timeOut</code> seconds. If element is not loaded in this time
	 * period org.openqa.selenium.TimeoutException exception is thrown
	 * 
	 * @param xPath
	 *            Name of the WebElement to wait for
	 * @param timeOut
	 *            Time Out in Seconds
	 * @return Reference to WebElement that represent waiting conditions
	 * @throws NoSuchElementException
	 *             If element was not found in current WebDrive context
	 */
	public WebElement waitForElementByXPath(String xPath, Integer timeOut)
			throws NoSuchElementException, org.openqa.selenium.TimeoutException {

		// /set waiting conditions
		return (new WebDriverWait(webDriver, timeOut))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(xPath)));
	}
	

	/**
	 * This method will wait for WebElement with class Name <code>className</code>
	 * for <code>timeOut</code> seconds. If element is not loaded in this time
	 * period org.openqa.selenium.TimeoutException exception is thrown
	 * 
	 * @param className
	 *            WebElement className
	 * @param timeOut
	 *            Time Out in Seconds
	 * @return Reference to WebElement that represent waiting conditions
	 * @throws NoSuchElementException
	 *             If element was not found in current WebDrive context
	 */
	public WebElement waitForElementByClassName(String className,
			Integer timeOut) throws NoSuchElementException,
			org.openqa.selenium.TimeoutException {

		// /set waiting conditions
		return (new WebDriverWait(webDriver, timeOut))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.className(className)));
	}
	
	/**
	 * Synonym for
	 * getWebDriver().findElement(By.className(
	 * className))
	 * 
	 * @param className
	 *            className of element to find
	 * @return Found WebElement
	 * @throws NoSuchElementException
	 *             If element was not found in current WebDrive context
	 */
	public WebElement elementByClassName(String className)
			throws NoSuchElementException {

		return webDriver.findElement(By.className(className));
	}
	
	/**
	 * Synonym for
	 * getWebDriver().findElement(By.id(
	 * id))
	 * 
	 * @param id
	 *            id of element to find
	 * @return Found WebElement
	 * @throws NoSuchElementException
	 *             If element was not found in current WebDrive context
	 */
	public WebElement elementById(String id)
			throws NoSuchElementException {

		return webDriver.findElement(By.id(id));
	}
}
