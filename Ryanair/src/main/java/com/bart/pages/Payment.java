package com.bart.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.bart.exceptions.PropertiesKeeperException;
import com.bart.exceptions.RyanairTestCommonException;
import com.bart.utils.PageTemplate;
import com.bart.utils.PropertiesKeeper;
import com.bart.utils.WebDriverKeeper;

public class Payment extends PageTemplate {
	private final static Logger log = Logger.getLogger(Payment.class.getName());

	public Payment() throws RyanairTestCommonException {
		waitForPageToLoad();
	}
	/**
	 * Waits for element at the bottom of the page
	 * @throws RyanairTestCommonException
	 */
	public void waitForPageToLoad() throws RyanairTestCommonException {
		log.info("Waiting for Payment page to load");
		// wait for pay now button at the bottom of page
		WebDriverKeeper.getInstance().waitForElementByXPath(
				"//button[@translate=\"common.components.payment_forms.pay_now\"]",
				PropertiesKeeper.getInstance().getPropAsInt("LongWait"));
	}

	/**
	 * Get List of all passanges fill forms
	 * @return list of WebElemnts corresponding to individual passangers
	 * @throws RyanairTestCommonException
	 */
	public List<WebElement> getPassangersList() throws RyanairTestCommonException {

		return WebDriverKeeper.getInstance().elementsByXPath("//passenger-input-group");
	}

	/**
	 * Fills inputs for passanger retrieved with getPassangersList()
	 * @param passanger
	 * @param tiltle
	 * @param name
	 * @param surname
	 */
	public void fillPassanger(WebElement passanger, String tiltle, String name, String surname) {
		log.info("Fill passanger details");
		if (tiltle != null) {
			log.info("Filling Tiltle " + tiltle);
			WebElement tiltleWe = passanger
					.findElement(By.xpath(".//div[@class=\"form-field payment-passenger-title\"]//select"));
			new Select(tiltleWe).selectByVisibleText(tiltle);
		}
		if (name != null) {
			log.info("Filling name " + name);
			WebElement nameWE = passanger
					.findElement(By.xpath(".//div[@class=\"form-field payment-passenger-first-name\"]//input"));
			nameWE.sendKeys(name);
		}
		if (surname != null) {
			log.info("Filling Surname " + surname);
			WebElement nameWE = passanger
					.findElement(By.xpath(".//div[@class=\"form-field payment-passenger-last-name\"]//input"));
			nameWE.sendKeys(surname);
		}

	}

	/**
	 * Fills inputs for "Contact" form 
	 * 
	 * @param emailAddress
	 * @param confirmEmailAddress
	 * @param country
	 * @param phone
	 * @throws NoSuchElementException
	 * @throws TimeoutException
	 * @throws RyanairTestCommonException
	 */
	public void fillContactInformation(String emailAddress, String confirmEmailAddress, String country, String phone)
			throws NoSuchElementException, TimeoutException, RyanairTestCommonException {
		log.info("Fill contact details");
		if (emailAddress != null) {
			log.info("Filling email " + emailAddress);
			WebElement emailWE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"emailAddress\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			emailWE.sendKeys(emailAddress);
		}
		if (confirmEmailAddress != null) {
			log.info("Filling confirm email " + confirmEmailAddress);
			WebElement confirmEmailWE = WebDriverKeeper.getInstance().waitForElementByXPath(
					"//input[@name=\"confirmEmail\"]", PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			confirmEmailWE.sendKeys(confirmEmailAddress);
		}
		if (country != null) {
			log.info("Filling country " + country);
			WebElement countryWE = WebDriverKeeper.getInstance().waitForElementByXPath("//select[@name=\"phoneNumberCountry\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			new Select(countryWE).selectByVisibleText(country);
		}
		if (phone != null) {
			log.info("Filling phone " + phone);
			WebElement phoneWE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"phoneNumber\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			phoneWE.sendKeys(phone);
		}
	}
	
	/**
	 * Fills inputs for "Credit card" form 
	 * 
	 * @param cardNumber
	 * @param cardType
	 * @param expiryMonth
	 * @param expiryYear
	 * @param CVV
	 * @param name
	 * @throws NoSuchElementException
	 * @throws TimeoutException
	 * @throws RyanairTestCommonException
	 */
	public void fillCreditCardInformation(String cardNumber, String cardType, String expiryMonth, String expiryYear, String CVV, String name )
			throws NoSuchElementException, TimeoutException, RyanairTestCommonException {
		log.info("Fill Credit Card details");
		if (cardNumber != null) {
			log.info("Filling cardNumber " + cardNumber);
			WebElement cardNumberWE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"cardNumber\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			cardNumberWE.sendKeys(cardNumber);
		}
		if (cardType != null) {
			log.info("Filling cardType " + cardType);
			WebElement cardTypeWE = WebDriverKeeper.getInstance().waitForElementByXPath("//select[@name=\"cardType\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			new Select(cardTypeWE).selectByVisibleText(cardType);
		}
		if (expiryMonth != null) {
			log.info("Filling expiryMonth " + expiryMonth);
			WebElement expiryMonthWE = WebDriverKeeper.getInstance().waitForElementByXPath("//select[@name=\"expiryMonth\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			new Select(expiryMonthWE).selectByVisibleText(expiryMonth);
		}
		if (expiryYear != null) {
			log.info("Filling expiryYear " + expiryYear);
			WebElement expiryYearWE = WebDriverKeeper.getInstance().waitForElementByXPath("//select[@name=\"expiryYear\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			new Select(expiryYearWE).selectByVisibleText(expiryYear);
		}
		if (CVV != null) {
			log.info("Filling CVV" + CVV);
			WebElement CVVWE = WebDriverKeeper.getInstance().waitForElementByXPath(
					"//input[@name=\"securityCode\"]", PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			CVVWE.sendKeys(CVV);
		}
		if (name != null) {
			log.info("Filling name " + name);
			WebElement nameWE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"cardHolderName\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			nameWE.sendKeys(name);
		}
		
	}
	
	/**
	 * Fills inputs for "Biling adress" form 
	 * @param address1
	 * @param address2
	 * @param city
	 * @param zip
	 * @param country
	 * @throws NoSuchElementException
	 * @throws TimeoutException
	 * @throws RyanairTestCommonException
	 */
	public void fillBillingAddress(String address1, String address2, String city, String zip, String country  )
			throws NoSuchElementException, TimeoutException, RyanairTestCommonException {
		log.info("Fill Billing Address");
		if (address1 != null) {
			log.info("Filling address1 " + address1);
			WebElement wE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"billingAddressAddressLine1\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			wE.sendKeys(address1);
		}
		if (address2 != null) {
			log.info("Filling address2 " + address2);
			WebElement wE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"billingAddressAddressLine2\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			wE.sendKeys(address2);
		}if (city != null) {
			log.info("Filling city " + city);
			WebElement wE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"billingAddressCity\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			wE.sendKeys(city);
		}if (zip != null) {
			log.info("Filling zip " + zip);
			WebElement wE = WebDriverKeeper.getInstance().waitForElementByXPath("//input[@name=\"billingAddressPostcode\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			wE.sendKeys(zip);
		}
		if (country != null) {
			log.info("Filling country " + country);
			WebElement countryWE = WebDriverKeeper.getInstance().waitForElementByXPath("//select[@name=\"billingAddressCountry\"]",
					PropertiesKeeper.getInstance().getPropAsInt("ShortWait"));
			new Select(countryWE).selectByVisibleText(country);
		}
		
	}
	
	public List<WebElement> clickConfirmTermsAndPayNow() throws NoSuchElementException, TimeoutException, RyanairTestCommonException{
		WebDriverKeeper.getInstance().elementByXPath("//input[@name=\"acceptPolicy\"]").click();	
		WebDriverKeeper.getInstance().elementByXPath("//button[@translate=\"common.components.payment_forms.pay_now\"]").click();
	
		waitForPageLoad();
		//get errors
		return WebDriverKeeper.getInstance().elementsByXPath("//li[@class=\"error\"]");	
	}
}
