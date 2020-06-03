package com.demo.resources;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

/* This class have before and after methods */
public class BaseTest extends Reporting{
	
	public static ThreadLocal<ExtentTest> extentTests = new ThreadLocal<>();
	static ExtentReports report;
	
	@BeforeSuite
	public void setUp() {
		report = getReportingInstance().createInstanceReport();
	}
	
	/*
	 * @parameters method
	 * 
	 * @returnType void
	 */
	// Method to initiate reporting and start execution
	@BeforeMethod
	public void startExecution(Method method) {
		try {
			// Fetching test case name
			testCaseName.set(method.getAnnotation(Test.class).testName());
			ExtentTest extentTest = report.createTest(testCaseName.get());
			// Assigning categories
			extentTest.assignCategory(getMultiFunctionInstance().getProp().getProperty(Constants.browser));
			// Assign Author
			extentTests.set(extentTest.assignAuthor(getMultiFunctionInstance().getProp().getProperty(Constants.testerName)));
			// Setting WebDriver
			tlDriver.set(getMultiFunctionInstance().setwDriver());
		} catch (Exception e) {
			// Reporting
			getUtil().report(Constants.error, Constants.exception, e.toString(), Constants.exception);
		}
	}

	/*
	 * @parameters result
	 * 
	 * @returnType void
	 */
	// Method to display execution completion execution status
	@AfterMethod
	public void getResult(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				extentTests.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
				// To give reason of failure
				extentTests.get().fail(result.getThrowable());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				extentTests.get().log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
			} else if (result.getStatus() == ITestResult.SKIP) {
				extentTests.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.GREY));
				// To give reason of skip
				extentTests.get().skip(result.getThrowable());
			}
		} catch (Exception e) {
			// Reporting
			getUtil().report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		} finally {
			// Closing driver
			tlDriver.get().quit();
			report.flush();
		}
	}

	/*
	 * @parameters NA
	 * 
	 * @returnType void
	 */
	// Method to close vmware and flush extendReport
	@AfterSuite
	public void tearDown() {
		try {
			// Closing vmware
			// Reporting
			//new CommonUtil().report(Constants.info, Constants.vmWare, Constants.closeVMWare, Constants.blank);
		} catch (Exception e) {
			// Reporting
			//getUtil().report(Constants.error, Constants.exception, Constants.blank + e, Constants.exception);
		} finally {
			try {
				if (getProp().getProperty("vmplatform").equals(Constants.ws) || getProp().getProperty("vmplatform").equals(Constants.fusion))
					Runtime.getRuntime().exec(Constants.command_vmClose);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tlDriver.remove();
		}
	}

	/*
	 * @parameters status, step
	 * 
	 * @returnType String
	 */
	// Method to create screenshot name and path
	public String getScreenShot(String status, String step) {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("dd_MM_yyyy");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();
		// Generating screenshotName
		String screenShotName = Constants.userDir + "/ScreenShots/" + date.format(now) + "/"
				+ getMultiFunctionInstance().getProp().getProperty("Browser") + "/" + testCaseName.get() + "/" + status + "/" + time.format(now) + "_" +step + ".jpg";
		// Type casting driver to TakeScreenshot
		TakesScreenshot ts = (TakesScreenshot) tlDriver.get();
		// Copy screenshot to screenShotName
		try {
			FileUtils.copyFile(ts.getScreenshotAs(OutputType.FILE), new File(screenShotName));
		} catch (IOException e) {
			extentTests.get().log(Status.ERROR, e);
			System.out.println(extentTests + " "+ Thread.currentThread().getName());
		}
		return screenShotName;
	}
	
	/*
	 * @parameters propertyName
	 * 
	 * @returnType String
	 */
	// Method to get system property
	public static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}
	
	/*
	 * @parameters dir
	 * 
	 * @returnType void
	 */
	// Method to delete directory for more than 10 days
	public void deleteDir(File dir) {
		try {
			if (dir.exists()) {
				int days = 10;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				BasicFileAttributes bfaReports = Files.readAttributes(dir.toPath(), BasicFileAttributes.class);
				String time = bfaReports.creationTime().toString().substring(0, 10);
				Date diff = simpleDateFormat.parse(time);
				long creationTime = new Date().getTime() - diff.getTime();
				if (days <= creationTime / (24 * 60 * 60 * 1000)) {
					dir.delete();
				}
			}
		} catch (IOException e) {
			// Printing exception
			System.out.println(Constants.exception + e);
		} catch (ParseException e) {
			// Printing exception
			System.out.println(Constants.exception + e);
		}
	}
}