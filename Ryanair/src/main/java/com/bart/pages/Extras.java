package com.bart.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.bart.exceptions.RyanairTestCommonException;
import com.bart.utils.PageTemplate;
import com.bart.utils.PropertiesKeeper;
import com.bart.utils.WebDriverKeeper;

/**
 * It contains methods that allow to interact with Extras page
 * @author bart
 *
 */
public class Extras extends PageTemplate {
	private final static Logger log = Logger.getLogger(Extras.class.getName());
	
	public Extras() throws RyanairTestCommonException{
		waitForPageToLoad();
	}
	
	/**
	 * Waits for element at the bottom of the page
	 * @throws RyanairTestCommonException
	 */
	public void waitForPageToLoad()  throws RyanairTestCommonException{
		log.info("Waiting for Extras page to load");
		//wait for checkout button at the bottom of page
		WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@translate=\"trips.summary.buttons.btn_checkout\"]", PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
	}
	
	/**
	 * Clicks "checkout" button and waits for popup asking to choose sits 
	 * 
	 * @return True if popup is present
	 * @throws RyanairTestCommonException
	 */
	public Boolean clickContinue() throws RyanairTestCommonException {
		log.info("Click checkout buton");
		WebElement button = WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@translate=\"trips.summary.buttons.btn_checkout\"]",
				PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
		button.click();
		
		try{
			WebElement rejectButton = WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@translate=\"trips.summary.seat.prompt.popup.reject\"]",
				PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
		}catch (org.openqa.selenium.TimeoutException e){			
			log.info("popup window was not presented");
			return false;
		}	
		log.info("popup window is presented");
		return true;

	}
	
	/**
	 * Closes Choose sits popup
	 * @return newly open Payment page
	 * @throws RyanairTestCommonException
	 */
	public Payment closePopUp() throws RyanairTestCommonException{
		WebElement rejectButton = WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@translate=\"trips.summary.seat.prompt.popup.reject\"]",
				PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
		rejectButton.click();
		return new Payment();
	}
	

}
