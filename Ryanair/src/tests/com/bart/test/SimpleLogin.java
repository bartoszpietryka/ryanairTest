package com.bart.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebElement;

import com.bart.TestTemplate;
import com.bart.exceptions.ExpectedException;
import com.bart.exceptions.NoSuchDriverException;
import com.bart.exceptions.RyanairTestCommonException;
import com.bart.pages.ChooseFlight;
import com.bart.pages.Extras;
import com.bart.pages.Payment;
import com.bart.pages.StartPage;
import com.bart.utils.WebDriverKeeper;

/**
 * This test class will try to buy ticket on Ryanair page.
 * 
 * @author bart
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class SimpleLogin extends TestTemplate {
	private final static Logger log = Logger.getLogger(SimpleLogin.class.getName());

	// Run tests against various browsers
	@Parameterized.Parameters
	public static Collection supportedBrowsers() {
		return Arrays.asList(new Object[][] { { "Chrome" }, { "Firefox" }

		});
	}

	public SimpleLogin(String browser) throws RyanairTestCommonException {
		WebDriverKeeper.setBrowser(browser);
	}

	/**
	 * This test will try to buy ticket. Except form starting URL, everything is
	 * hardcoded. In normal tests We would usually read all input data from
	 * properties files. It will fail on last step because Credit card data is
	 * incorrect .
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_SimpleBuyTicket() throws Exception {
		try {
			log.info("Starting test: " + getMethodName());
			StartPage firstPage = new StartPage(); // go to page specified in
													// test.properties file
			firstPage.setFromInput("DUB");
			List<WebElement> fromCityList = firstPage.getFromCityList();
			assertEquals("One city on list", 1, fromCityList.size());
			assertEquals("Dublin on list", "Dublin", fromCityList.get(0).getText());
			log.info("Click Dublin");
			fromCityList.get(0).click();

			firstPage.setToInput("SXF");
			List<WebElement> toCityList = firstPage.getToCityList();
			assertEquals("One city on list", 1, toCityList.size());
			assertEquals("Berlin (SXF) on list", "Berlin (SXF)", toCityList.get(0).getText());
			log.info("Click Berlin (SXF)");
			toCityList.get(0).click();

			firstPage.setFlyOutDateInput("28", null, null);
			firstPage.setFlyBackDateInput("29", null, null);
			firstPage.setPassangersNumber("2", null, null, null);
			ChooseFlight chooseFlightPage = firstPage.clickLetsGo();

			List<WebElement> regularFlightsList = chooseFlightPage
					.getRegularFlightsList(chooseFlightPage.getOutbound());
			// We fill chooses flight from top of the table
			assertTrue("Outbound flight present on list", regularFlightsList.size() > 0);
			String outboundPrice = chooseFlightPage.getPrice(regularFlightsList.get(0));
			assertNotNull("Price avalaible", outboundPrice);
			log.info("Outbound price" + outboundPrice);
			chooseFlightPage.selectFlight(regularFlightsList.get(0));

			// return flight
			regularFlightsList = chooseFlightPage.getRegularFlightsList(chooseFlightPage.getInbound());
			// We fill chooses flight from top of the table
			assertTrue("Outbound flight present on list", regularFlightsList.size() > 0);
			String inboundPrice = chooseFlightPage.getPrice(regularFlightsList.get(0));
			assertNotNull("Price avalaible", inboundPrice);
			log.info("inbound price" + inboundPrice);
			chooseFlightPage.selectFlight(regularFlightsList.get(0));
			Extras extrasPage = chooseFlightPage.clickContinue();

			assertTrue("Popup windows asking for seat selection present", extrasPage.clickContinue());
			Payment paymentPage = extrasPage.closePopUp();

			List<WebElement> passangersList = paymentPage.getPassangersList();
			assertEquals("Two passangers on list", 2, passangersList.size());
			paymentPage.fillPassanger(passangersList.get(0), "Mr", "James", "Bond");
			paymentPage.fillPassanger(passangersList.get(1), "Mrs", "Lara", "Croft");
			paymentPage.fillContactInformation("bond@gmail.com", "bond@gmail.com", "Germany", "111111111");
			paymentPage.fillCreditCardInformation("5555 5555 5555 5557", "MasterCard", "11", "2020", "000",
					"James Bond");
			paymentPage.fillBillingAddress("adress 1", "adress 2", "Munich", "555-555", "Germany");
			List<WebElement> errors = paymentPage.clickConfirmTermsAndPayNow();
			for (WebElement error : errors) {
				log.warn("Validation error" + error.getText());
			}
			assertTrue("Verification errors on payment page", errors.isEmpty());
		} catch (Exception e) {
			log.error("Exception encountered in script: " + getMethodName() + " . Exception class : "
					+ e.getClass().getName());
			throw e;
		} finally {
			log.info("Ending test: " + getMethodName());
		}
	}

	/**
	 * Offten it more practical to actually throw custom exeception class. Or
	 * expect test to fail with selenium.common.exceptions
	 * 
	 * @throws Exception
	 *             We expect jUnit to mark test as Successful only if exception
	 *             of certain class and text is thrown
	 */
	@Test
	public void test02_TestWithException() throws Exception {
		try {
			log.info("Starting test: " + getMethodName());
			StartPage firstPage = new StartPage(); // go to page specified in
													// test.properties file

			thrown.expect(ExpectedException.class);
			thrown.expectMessage(containsString("BAD LUCK"));
			log.info("Expecting message: BAD LUCK");
			firstPage.throwSomething();
		} catch (Exception e) {
			log.error("Exception encountered in script: " + getMethodName() + " . Exception class : "
					+ e.getClass().getName());
			throw e;
		} finally {
			log.info("Ending test: " + getMethodName());
		}
	}
}
