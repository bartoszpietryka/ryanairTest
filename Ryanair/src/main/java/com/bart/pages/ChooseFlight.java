package com.bart.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.bart.exceptions.RyanairTestCommonException;
import com.bart.utils.PageTemplate;
import com.bart.utils.PropertiesKeeper;
import com.bart.utils.WebDriverKeeper;

/**
 * It contains methods that allow to interact with Choose flight page
 * 
 * @author bart
 *
 */
public class ChooseFlightddtt extends PageTemplate {
	private final static Logger log = Logger.getLogger(ChooseFlight.class.getName());
	
	public ChooseFlight () throws RyanairTestCommonException{
		waitForPageToLoad();
	}

	/**
	 * Waits for element at the bottom of the page
	 * @throws RyanairTestCommonException
	 */
	public void waitForPageToLoad()  throws RyanairTestCommonException{
		log.info("Waiting for Choose Flight page to load");
		//wait for continue button at the bottom of page
		WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@id=\"continue\"]", PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
	}

	/**
	 * Gets web element with tag  <flight-list id="outbound">  it contains all out flights
	 * @return WebElement for Out flights
	 * @throws NoSuchElementException
	 * @throws RyanairTestCommonException
	 */
	public WebElement getOutbound() throws NoSuchElementException, RyanairTestCommonException{
		return WebDriverKeeper.getInstance().elementById("outbound");
	}
	
	/**
	 * Gets web element with tag  <flight-list id="inbound">  it contains all return flights
	 * @return WebElement for return flights
	 * @throws NoSuchElementException
	 * @throws RyanairTestCommonException
	 */
	public WebElement getInbound() throws NoSuchElementException, RyanairTestCommonException{
		return WebDriverKeeper.getInstance().elementById("inbound");
	}
	
	/**
	 * Gets text from web element corresponding to flight
	 * @param flight Element retrived by getRegularFlightsList
	 * @return String with flight price
	 */
	public String getPrice(WebElement flight){
		return flight.findElement(By.className("price")).getText(); 
	}
	
	/**
	 * Clicks on element corresponding to flight
	 * @param flight Element retrived by getRegularFlightsList
	 * @return true if checkbox is checked 
	 */
	public Boolean selectFlight(WebElement flight){
		WebElement radio =flight.findElement(By.className("core-radio"));
		Boolean isChecked = radio.isSelected();
		if (!isChecked.equals(true))
			flight.click();
		return isChecked;
	}
	
	/**
	 * Gets flights with  from "Regular" column
	 * @param flightsList 
	 * @return List of flights
	 * @throws RyanairTestCommonException
	 */
	public List<WebElement> getRegularFlightsList(WebElement flightsList) throws RyanairTestCommonException {
		
		return flightsList.findElements(By.xpath(".//div[@class=\"one-third regular\"]"));
	}
	
	/**
	 * Clicks "Continue" button and waits for Extras to load
	 * @return newly Extras page
	 * @throws RyanairTestCommonException
	 */
	public Extras clickContinue() throws RyanairTestCommonException {
		log.info("Click continue buton");
		WebElement button = WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@id=\"continue\"]",
				PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
		button.click();
		return new Extras();

	}
}
