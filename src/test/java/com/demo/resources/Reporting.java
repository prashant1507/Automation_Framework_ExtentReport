package com.demo.resources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

/* This class is to create and return instance of ExtentReports */
public class Reporting extends MultiFunction {

	public static ExtentReports extentReports;
	public static ThreadLocal<ExtentHtmlReporter> extentHtmlReporter = new ThreadLocal<>();

	/*
	 * @parameters NA
	 * 
	 * @returnType ExtentReports
	 */
	public ExtentReports createInstanceReport() {
		if (!new File(Constants.userDir + "/Reports/").exists()) {
			new File(Constants.userDir + "/Reports/").mkdirs();
		}
		extentReports = new ExtentReports();
		// To generate report with name
		extentHtmlReporter.set(new ExtentHtmlReporter(Constants.userDir + "/Reports/" + "ExecutionReport_"
				+ new SimpleDateFormat(Constants.date).format(new Date()) + ".html"));

		// Setting Document Title
		extentHtmlReporter.get().config().setDocumentTitle("Report");
		// Setting Report Name
		extentHtmlReporter.get().config().setReportName("Report");
		// Setting Theme
		extentHtmlReporter.get().config().setTheme(Theme.STANDARD);
		// Setting Chart location
		extentHtmlReporter.get().config().setTestViewChartLocation(ChartLocation.TOP);
		// Setting Chart visibility
		extentHtmlReporter.get().config().setChartVisibilityOnOpen(true);
		// Setting encoding
		extentHtmlReporter.get().config().setEncoding("UTF-8");
		// Setting Time stamp
		extentHtmlReporter.get().config().setTimeStampFormat("yyyy/MM/dd HH:mm:ss");  //E MMM dd, yyyy hh:mm:ss a zzz
		
		// Setting system info
		extentReports.setSystemInfo("Name", getProp().getProperty(Constants.testerName));
		extentReports.setSystemInfo("Environment", getProp().getProperty(Constants.environment));
		extentReports.setSystemInfo("Browser", getProp().getProperty(Constants.browser));
		extentReports.setAnalysisStrategy(AnalysisStrategy.TEST);			
		// Setting append exist as true
		extentHtmlReporter.get().setAppendExisting(true);
		extentReports.attachReporter(extentHtmlReporter.get());
		
		return extentReports;
	}
}
