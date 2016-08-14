package com.bart.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.bart.exceptions.ExpectedException;
import com.bart.exceptions.RyanairTestCommonException;
import com.bart.utils.PageTemplate;
import com.bart.utils.PropertiesKeeper;
import com.bart.utils.WebDriverKeeper;

/**
 * It contains methods that allow to interact with Ryanair front page
 * 
 * @author bart
 *
 */
public class StartPage extends PageTemplate {
	private final static Logger log = Logger.getLogger(StartPage.class.getName());

	/**
	 * Navigates to <code>URL</code>
	 * 
	 * @param URL
	 *            URL address in <code>http://domain/path</code> format
	 * @throws RyanairTestCommonException 
	 */
	public StartPage(String URL) throws RyanairTestCommonException {
		log.info("Navigating to start page");
		WebDriverKeeper.getInstance().goToPage("https://www.ryanair.com/ie/en/");
		log.info("Navigating to start page END");
	}

	/**
	 * Navigates to page specified with property "StartPage"
	 */
	public StartPage() throws RyanairTestCommonException {
		log.info("Navigating to start page");
		WebDriverKeeper.getInstance().goToPage(PropertiesKeeper.getInstance().getProp("StartPage"));
		log.info("Navigating to start page END");
	}

	/**
	 * Set input field "From"
	 * 
	 * @param location
	 *            will used in input field
	 * @throws RyanairTestCommonException 
	 * @throws NoSuchElementException 
	 */
	public void setFromInput(String location) throws NoSuchElementException, RyanairTestCommonException {
		WebElement fromInput = WebDriverKeeper.getInstance()
				.elementByXPath(("//input[@aria-labelledby=\"label-airport-selector-from\"]"));
		fromInput.clear();
		fromInput.sendKeys(location);

	}

	/**
	 * Set input field "To"
	 * 
	 * @param location
	 *            will used in input field
	 * @throws RyanairTestCommonException 
	 * @throws NoSuchElementException 
	 */
	public void setToInput(String location) throws NoSuchElementException, RyanairTestCommonException {
		WebElement fromInput = WebDriverKeeper.getInstance()
				.elementByXPath(("//input[@aria-labelledby=\"label-airport-selector-to\"]"));
		fromInput.clear();
		fromInput.sendKeys(location);

	}

	/**
	 * Get list of avaliable cities in menu, after "From" field was filled
	 * 
	 * @return list of "div" WebElements
	 * @throws RyanairTestCommonException
	 */
	public List<WebElement> getFromCityList() throws RyanairTestCommonException {
		// multiple classes. waitForElementByClassName won't work
		WebDriverKeeper.getInstance().waitForElementByXPath(
				"//div[@class=\"core-list-item core-list-item-rounded initial\"]",
				PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));

		return WebDriverKeeper.getInstance().elementsByXPath("//span[@ng-bind-html]");
	}

	/**
	 * Get list of avaliable cities in menu, after "To" field was filled
	 * 
	 * @return list of "div" WebElements
	 * @throws RyanairTestCommonException
	 */
	public List<WebElement> getToCityList() throws RyanairTestCommonException {
		// At least one element has to be on list. Otherwise there will be
		// timeout
		WebDriverKeeper.getInstance().waitForElementByXPath("//div[@class=\"core-list-item core-list-item-rounded\"]",
				PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
		return WebDriverKeeper.getInstance().elementsByXPath("//span[@ng-bind-html]");
	}

	protected void setDateFields(WebElement dateInput, String day, String month, String year)
			throws RyanairTestCommonException {
		if (day != null) {
			WebElement dd = dateInput.findElement(By.xpath(".//input[@class=\"dd date-input-0\"]"));
			dd.sendKeys(day);
		}
		if (month != null) {
			WebElement mm = dateInput.findElement(By.xpath(".//input[@class=\"mm date-input-1\"]"));
			mm.sendKeys(month);
		}
		if (month != null) {
			WebElement yyyy = dateInput.findElement(By.xpath(".//input[@class=\"yyyy date-input-2\"]"));
			yyyy.sendKeys(month);
		}
	}

	/**
	 * Sets input fields for day, month and year of departure . Ignored if null
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @throws RyanairTestCommonException
	 */
	public void setFlyOutDateInput(String day, String month, String year) throws RyanairTestCommonException {
		log.info("set Fly Out date");
		// select whole node
		WebElement fromDateInputs = WebDriverKeeper.getInstance().waitForElementByXPath(
				"//div[@class=\"col-cal-from\"]", PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
		setDateFields(fromDateInputs, day, month, year);
		//we must wait until popup menu is open. Sometimes it does not expand on time and there clicking let's go is impossible 
		WebDriverKeeper.getInstance().waitForElementByXPath(
				"//div[@class=\"col-cal-from\"]//div[@aria-expanded=\"true\"]", PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
	}

	/**
	 * Sets input fields for day, month and year of return flight . Ignored if
	 * null
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @throws RyanairTestCommonException
	 */
	public void setFlyBackDateInput(String day, String month, String year) throws RyanairTestCommonException {
		log.info("set Fly Back date");
		WebElement toDateInputs = WebDriverKeeper.getInstance().waitForElementByXPath("//div[@class=\"col-cal-to\"]",
				PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
		setDateFields(toDateInputs, day, month, year);
		//we must wait until popup menu is open. Sometimes it does not expand on time and there clicking let's go is impossible 
				WebDriverKeeper.getInstance().waitForElementByXPath(
						"//div[@class=\"col-cal-to\"]//div[@aria-expanded=\"true\"]", PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
	}

	/**
	 * sets number of passangers. It uses input fields
	 * 
	 * @param adults
	 * @param teens
	 * @param children
	 * @param Infants
	 * @throws RyanairTestCommonException
	 */
	public void setPassangersNumber(String adults, String teens, String children, String Infants)
			throws RyanairTestCommonException {
		log.info("set number of passangers");
		// get node for setting passengers number
		WebElement node = WebDriverKeeper.getInstance().waitForElementByXPath("//div[@class=\"col-passengers\"]",
				PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
		// click to open menu
		node.findElement(By.className("value")).click();
		if (adults != null) {
			WebElement adultsWE = node.findElement(By.xpath(".//input[@aria-label=\"Adults 16+ years\"]"));
			adultsWE.clear();
			adultsWE.sendKeys(adults);
		}

	}

	/**
	 * Clicks "Let's go" button and waits for Choose Flight page to load
	 * 
	 * @return newly open Choose Flight page
	 * @throws RyanairTestCommonException
	 */
	public ChooseFlight clickLetsGo() throws RyanairTestCommonException {
		log.info("Click lets's go buton");
		WebElement button = WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@class=\"core-btn-primary core-btn-block core-btn-big\"]",
				PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
		button.click();
		return new ChooseFlight();

	}
	
	public void throwSomething() throws RyanairTestCommonException {
		log.info("Throw exception");
		throw new ExpectedException("BAD LUCK");

	}
}
