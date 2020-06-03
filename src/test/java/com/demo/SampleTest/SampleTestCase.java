package com.demo.SampleTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.demo.resources.BaseTest;
import com.demo.resources.CommonUtil;
import com.demo.resources.Constants;
import com.demo.resources.ObjectRepo;
import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;

/* This class is to define test cases */
public class SampleTestCase extends BaseTest {

	/*
	 * @Arguments: NA
	 * 
	 * @Return Type: void
	 * 
	 * @Test Case: To setup vmware for execution
	 * 
	 * @Author: Test Man
	 * 
	 * @Date: 25/09/2018
	 */
	@Test(testName = "vmware")
	public void vmwareSetUp() {
		ExtentTest extentTest = extentTests.get();
		try {
			if (getProp().getProperty("vmplatform").equals(Constants.ws)
					|| getProp().getProperty("vmplatform").equals(Constants.fusion)) {
				Process pr = Runtime.getRuntime().exec(Constants.path_vmWare + Constants.singleSpace
						+ getMultiFunctionInstance().getProp().getProperty(Constants.vmurl));
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String command;
				while ((command = stdInput.readLine()) != null) {
					if (!command.equals(Constants.blank)) {
						// adding condition
						if (command.equalsIgnoreCase(Constants.snapShotNotExist)
								|| command.equalsIgnoreCase(Constants.fileNotExist)
								|| command.equalsIgnoreCase(Constants.fileNotFound)
								|| command.contains(Constants.virtualMachineNotExist)
								|| command.equalsIgnoreCase(Constants.fileAlreadyInUse)
								|| command.equalsIgnoreCase(Constants.invalidLogin)) {
							// Log reporting
							extentTest.log(Status.FAIL, "<b>" + command + "</b>");
							extentTest.log(Status.FAIL, Constants.urlNotLaunched);
							extentTest.log(Status.INFO, Constants.terminated);
							// closing browser
							tlDriver.get().quit();
							// Generate report
							tearDown();
							Runtime.getRuntime().exit(0);
						} else
							getUtil().report(Constants.pass, Constants.vmWare, Constants.htmlBreak + command,
									Constants.blank);
					}
				}
			} else if (getProp().getProperty("vmplatform").equals(Constants.esx)) {
				// To run on remote machine
				PowerShellResponse response;
				// Map to set maxWait
				Map<String, String> maxWait = new HashMap<String, String>();
				maxWait.put("maxWait", "600000");
				//maxWait.put("waitPause", "5000");
			    PowerShell powerShell = PowerShell.openSession().configuration(maxWait);
			    // Commands to run remote machine
				String acceptInvalidCert = "Set-PowerCLIConfiguration -InvalidCertificateAction Ignore -Confirm:$false";
				// Set connection address, user and password
				String connectServer = "Connect-VIServer -Server 10.10.10.11 -User 'root' -Password 'P@ssw0rd'";
				// Set vm and snapshot name 
				String startRevertVM = "Set-VM -VM windows10 -Snapshot snapshotvm -Confirm:$false";
				// Set vm name
				String waitVMTool = "Wait-Tools -VM windows10";
				// Set user, password, vm, script path and job name
				String schTask = "Invoke-VMScript -vm windows10 -guestUser shibu -guestPassword password -ScriptText 'schtasks /create /tn task100 /f /tr \"C:/Users/shibu/Desktop/launchBrowser.bat file://C:/Windows/System32/calc.exe\" /sc weekly'";
				// Set user, password, vm and job name
				String runTask = "Invoke-VMScript -vm windows10 -guestUser shibu -guestPassword password -ScriptText 'schtasks /run /tn task100'";
				// Set user, password, vm and job name
				String disableTask = "Invoke-VMScript -vm windows10 -guestUser shibu -guestPassword password -ScriptText 'schtasks /change /tn task100 /disable'";
				String[] esxCommands = {acceptInvalidCert, connectServer, startRevertVM, waitVMTool, schTask, runTask, disableTask};
				
				for (int i = 0; i<esxCommands.length; i++) {
					response = powerShell.executeCommand(esxCommands[i]); Thread.sleep(5000);
					// Reporting fail if command fails
					if (response.getCommandOutput().contains("CategoryInfo")) {
						getUtil().report(Constants.fail, Constants.esx, Constants.htmlBreak + esxCommands[i] + Constants.htmlBreak + response.getCommandOutput(), Constants.blank);
					} else {
						getUtil().report(Constants.pass, Constants.esx, Constants.htmlBreak + esxCommands[i] + Constants.htmlBreak + response.getCommandOutput(), Constants.blank);
					}
				}
				// Closing powershell session
				powerShell.close();
			}
		} catch(PowerShellNotAvailableException e) {
			// Log reporting
			extentTest.log(Status.ERROR, e);
		  } catch (Exception e) {
			// Log reporting
			extentTest.log(Status.ERROR, e);
		}
	}

	/*
	 * @Arguments: NA
	 * 
	 * @Return Type: void
	 * 
	 * @Test Case: To find the subtraction of two numbers
	 * 
	 * @Author: Test Man
	 * 
	 * @Date: 25/09/2018
	 */

	@Test(testName = "sampleTest1")
	public void demoTest1() {
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
	 * 
	 * @Return Type: void
	 * 
	 * @Test Case: To find the addition of two numbers
	 * 
	 * @Author: Test Man
	 * 
	 * @Date: 25/09/2018
	 */

	@Test(testName = "sampleTest2")
	public void demoTest2() {
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

	@Test(testName = "sampleTest3")
	public void demoTest3() {
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

	@Test(testName = "sampleTest4")
	public void demoTest4() {
		CommonUtil util = getUtil();
		try {
			// Sending value to text box 1
			util.sendKeys(ObjectRepo.TextBox_First, "40");
			// Sending value to text box 2
			util.sendKeys(ObjectRepo.TextBox_Second, "40");
			// Clicking on go button
			util.clickElement(ObjectRepo.Button_Go);
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
	}

	@Test(testName = "skip")
	public void skip() {
		CommonUtil util = getUtil();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
		throw new SkipException("skip test case");
	}

	@Test(testName = "sampleTest5")
	public void demoTest5() {
		CommonUtil util = getUtil();
		try {
			// Sending value to text box 1
			util.sendKeys(ObjectRepo.TextBox_First, "500");
			// Sending value to text box 2
			util.sendKeys(ObjectRepo.TextBox_Second, "40");
			// Clicking on go button
			util.clickElement(ObjectRepo.Button_Go);
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
	}

	@Test(testName = "sampleTest6")
	public void demoTest6() {
		CommonUtil util = getUtil();
		try {
			// Sending value to text box 1
			util.sendKeys(ObjectRepo.TextBox_First, "506");
			// Sending value to text box 2
			util.sendKeys(ObjectRepo.TextBox_Second, "40");
			// Clicking on go button
			util.clickElement(ObjectRepo.Button_Go);
		} catch (Exception e) {
			// Reporting
			util.report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		}
	}

	@Test(testName = "skip1")
	public void skip1() {
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
