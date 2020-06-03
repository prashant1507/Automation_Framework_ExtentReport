package com.demo.SampleTest;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.demo.resources.BaseTest;
import com.demo.resources.CommonUtil;
import com.demo.resources.Constants;
import com.demo.resources.ObjectRepo;

/* This class is to define test cases */
public class SampleTestCase2 extends BaseTest {

	/*
	 * @Arguments: NA 
	 * @Return Type: void 
	 * @Test Case: To find the subtraction of two numbers 
	 * @Author: Test Man 
	 * @Date: 25/09/2018
	 */

	@Test(testName = "sampleTest2_1")
	public void demoTest2_1() {
		CommonUtil util = getUtil();
		try {
			
			// Element enable
			util.isEnabled(ObjectRepo.TextBox_First);
			// Element displayed
			util.isDisplayed(ObjectRepo.TextBox_First);
			// Element selected
			// util.isSelected(ObjectRepo.TextBox_First);
			// Sending value to text box 1
			util.sendKeys(ObjectRepo.TextBox_First, "5");
			// Selecting '-' from drop down
			util.selectDropDownByValue(ObjectRepo.DropDown, "SUBTRACTION");
			// Sending value to text box 2
			util.sendKeys(ObjectRepo.TextBox_Second, "4");
			// Clicking on go button
			util.clickElement(ObjectRepo.Button_Go);
			// Comparing result
			util.stringCompare(util.getText(ObjectRepo.Text_Result), "1");
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
	}

	/*
	 * @Arguments: NA 
	 * @Return Type: void 
	 * @Test Case: To find the addition of two numbers 
	 * @Author: Test Man 
	 * @Date: 25/09/2018
	 */

	@Test(testName = "sampleTest2_2")
	public void demoTest2_2() {
		CommonUtil util = getUtil();
		try {
			// Sending value to text box 1
			util.sendKeys(ObjectRepo.TextBox_First, "50");
			// Sending value to text box 2
			util.sendKeys(ObjectRepo.TextBox_Second, "40");
			// Clicking on go button
			util.clickElement(ObjectRepo.Button_Go);
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
	} 
	
	@Test(testName = "sampleTest2_3")
	public void demoTest2_3() {
		CommonUtil util = getUtil();
		try {
			// Sending value to text box 1
			util.sendKeys(ObjectRepo.TextBox_First, "60");
			// Sending value to text box 2
			util.sendKeys(ObjectRepo.TextBox_Second, "40");
			// Clicking on go button
			util.clickElement(ObjectRepo.Button_Go);
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
	}
	
	
	@Test(testName = "skip2_4")
	public void skip2_4() {
		CommonUtil util = getUtil();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
		throw new SkipException("skip test case");
	}
}
