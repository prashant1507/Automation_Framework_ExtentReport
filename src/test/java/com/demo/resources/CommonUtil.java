package com.demo.resources;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/* This class is to define common methods */
public class CommonUtil extends BaseTest{
	/*
	 * @parameters status, identifier, message, step
	 * 
	 * @returnType void
	 */
	// Method to report based on status
	public void report(String status, String identifier, String message, String step) {
		try {
			ExtentTest extentTest = extentTests.get();
			// Fail reporting
			if (status.contains(Constants.fail)) {
				// Storing screenshot path
				String screenshotPath = getScreenShot(status, step);
				// Adding screenshot in failed step
				extentTest.fail(identifier + Constants.reason + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
				Assert.fail(message);
			}
			// Pass reporting
			else if (status.equalsIgnoreCase(Constants.pass)) {
				// Storing screenshot path
				String screenshotPath = getScreenShot(status, step);
				// Adding screenshot pass step
				extentTest.pass(Constants.element+identifier+ Constants.singleSpace+message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			}
			// Info reporting
			else if (status.equalsIgnoreCase(Constants.info))
				extentTest.log(Status.INFO, identifier+ Constants.singleSpace +message);
			// Error reporting
			else if (status.equalsIgnoreCase(Constants.error)) {
				extentTest.error(identifier+ Constants.reason +message);
				Assert.fail(message);
			}
		} catch (Exception e) {
			// Reporting
			report(Constants.error, identifier, e.toString(), Constants.exception);
		}
	}

	/*
	 * @parameters actualResult, expectedResult
	 * 
	 * @returnType void
	 */
	// Method to report based on actual and expected result
	public void reportStringMatch(String actualResult, String expectedResult) {
		if (expectedResult.equals(actualResult)) {
			// Reporting
			extentTests.get().pass(Constants.resultMatched + Constants.actual + actualResult + Constants.expected + expectedResult);
		} else {
			// Reporting
			extentTests.get().fail(Constants.resultNotMatched + Constants.actual + actualResult + Constants.expected + expectedResult);
			Assert.fail("String not matched");
		}
	}

	/*
	 * @parameters locator, keys
	 * 
	 * @returnType void
	 */
	// Method to send keys to locator
	public void sendKeys(ObjectRepo locator, String keys) {
		try {
			// Sending keys to locator
			tlDriver.get().findElement(locator.getLocator()).sendKeys(keys);
			// Reporting
			report(Constants.pass, locator.getIdentifier(), Constants.keySend + keys, locator.getIdentifier());
		} catch (Exception e) {
			// Reporting
			report(Constants.fail, Constants.elementNotFound + locator.getIdentifier(), e.toString(), locator.getIdentifier());
		}
	}

	/*
	 * @parameters locator
	 * 
	 * @returnType void
	 */
	// Method to click locator
	public void clickElement(ObjectRepo locator) {
		try {
			// Clicking on element
			tlDriver.get().findElement(locator.getLocator()).click(); 
			// Reporting
			report(Constants.pass, locator.getIdentifier(), Constants.isClicked, locator.getIdentifier());
		} catch (Exception e) {
			// Reporting
			report(Constants.fail, Constants.elementNotFound + locator.getIdentifier(), e.toString(), locator.getIdentifier());
		}
	}
	

	/*
	 * @parameters locator
	 * 
	 * @returnType String
	 */
	// Method to get text from element
	public String getText(ObjectRepo locator) {
		String text = null;
		try { 
			// Get text
			Thread.sleep(3000);
			text = tlDriver.get().findElement(locator.getLocator()).getText();
			// Reporting
			report(Constants.pass, locator.getIdentifier(), Constants.textPresent + text, locator.getIdentifier());
		} catch (Exception e) {
			// Reporting
			report(Constants.fail, Constants.elementNotFound + locator.getIdentifier(), e.toString(), locator.getIdentifier());
		}
		return text;
	}
		
	/*
	 * @parameters locator
	 * 
	 * @returnType boolean
	 */
	// Method to check element is displayed or not
	public boolean isDisplayed(ObjectRepo locator) {
		boolean flag = false;
		try {
			// Checking is element displayed
			 flag = tlDriver.get().findElement(locator.getLocator()).isDisplayed();
			 if(flag==true)
				 // Reporting
				 report(Constants.pass, locator.getIdentifier(), Constants.elementDisplayed + flag, locator.getIdentifier());
			 else
				 // Reporting
				 report(Constants.fail, Constants.elementNotDisplayed+locator.getIdentifier(), Constants.htmlBreak+flag, locator.getIdentifier());
		} catch (Exception e) {
			// Reporting
			report(Constants.fail, Constants.elementNotDisplayed+locator.getIdentifier(), flag+Constants.htmlBreak+e.toString(), locator.getIdentifier());
		}
		return flag;
	}
	
	/*
	 * @parameters locator
	 * 
	 * @returnType boolean
	 */
	// Method to check element is enabled or not
	public boolean isEnabled(ObjectRepo locator) {
		boolean flag = false;
		try {
			// Checking element is enable
			 flag = tlDriver.get().findElement(locator.getLocator()).isEnabled();
			 if(flag==true)
				 // Reporting
				 report(Constants.pass, locator.getIdentifier(), Constants.elementEnabled + flag, locator.getIdentifier());
			 else
				 // Reporting
				 report(Constants.fail, Constants.elementNotEnabled+locator.getIdentifier(), Constants.blank+flag, locator.getIdentifier());
		} catch (Exception e) {
			// Reporting
			report(Constants.fail, Constants.elementNotEnabled+locator.getIdentifier(), flag+Constants.htmlBreak+e.toString(), locator.getIdentifier());
		}
		return flag;
	}
	
	/*
	 * @parameters locator
	 * 
	 * @returnType boolean
	 */
	// Method to check element is selected or not
	public boolean isSelected(ObjectRepo locator) {
		boolean flag = false;
		try {
			// Checking element is selected
			 flag = tlDriver.get().findElement(locator.getLocator()).isSelected();
			 if(flag==true)
				 // Reporting
				 report(Constants.pass, locator.getIdentifier(), Constants.elementSelected + flag, locator.getIdentifier());
			 else
				 // Reporting
				 report(Constants.fail, Constants.elementNotSelected+locator.getIdentifier(), Constants.blank+flag, locator.getIdentifier());
		} catch (Exception e) {
			// Reporting
			report(Constants.fail, Constants.elementNotSelected+locator.getIdentifier(), flag+Constants.htmlBreak+e.toString(), locator.getIdentifier());
		}
		return flag;
	}

	/*
	 * @parameters actual, expected
	 * 
	 * @returnType void
	 */
	// Method to check string are equal
	public void stringCompare(String actual, String expected) {
		// Reporting
		reportStringMatch(actual, expected);
	}
	
	/*
	 * @parameters locator, value
	 * 
	 * @returnType void
	 */
	// Method to select option from drop down by value
	public void selectDropDownByValue(ObjectRepo locator, String value) {
		try {
			// DropDown
			Select option = new Select(tlDriver.get().findElement(locator.getLocator()));
			// Selecting value
			option.selectByValue(value);
			// Reporting
			report(Constants.pass, locator.getIdentifier(), Constants.optionSelected + value, locator.getIdentifier());
		} catch (Exception e) {
			// Reporting
			report(Constants.fail, Constants.elementNotFound+locator.getIdentifier(), value+Constants.htmlBreak+e.toString(), locator.getIdentifier());
		}	
	}
	
}
