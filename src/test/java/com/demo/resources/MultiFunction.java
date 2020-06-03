package com.demo.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
// import java.net.MalformedURLException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;

/* This class is to create and store objects */
public class MultiFunction{

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	public static ThreadLocal<String> testCaseName = new ThreadLocal<>();
	public static ThreadLocal<CommonUtil> util = ThreadLocal.withInitial(() -> new CommonUtil());

	@Parameters("browser") // To get browser value from TestNG.xml. and pass argument to this function as String browser
	public synchronized WebDriver setwDriver() {
		ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
		String browser = getProp().getProperty(Constants.browser);
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(CapabilityType.BROWSER_NAME, browser);
			cap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty(Constants.driver_Chrome, Constants.path_ChromeDriver);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//For Local Usage
	            tlDriver.set(new ChromeDriver(cap));
	            
	            // For grid
	            //tlDriver.set(new RemoteWebDriver(new URL("URL Here"), cap));
	            
			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty(Constants.driver_Firefox, Constants.path_FirefoxDriver);
				FirefoxOptions options = new FirefoxOptions();
				FirefoxProfile profile = new FirefoxProfile();
				// Accept Untrusted Certificates
				profile.setAcceptUntrustedCertificates(true);
				profile.setAssumeUntrustedCertificateIssuer(false);
				// Use no Proxy Settings
				profile.setPreference("network.proxy.type", 0);
				// Set Firefox profile to capabilities
				options.setCapability(FirefoxDriver.PROFILE, profile);
				// For Local Usage
				tlDriver = ThreadLocal.withInitial(() -> new FirefoxDriver(options));

				// For Grid Usage 
				// tlDriver.set(new RemoteWebDriver(new URL("URL Here"), options));			
				
			} else if (browser.equalsIgnoreCase("internet explorer")) {
				System.setProperty(Constants.driver_IE, Constants.path_InternetExplorerDriver);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				// For Local Usage
				tlDriver = ThreadLocal.withInitial(() -> new InternetExplorerDriver(cap));
				// For grid
	            //tlDriver.set(new RemoteWebDriver(new URL("URL Here"), cap));
			}
			// Maximizing browser
			tlDriver.get().manage().window().maximize();
			// Get URL
			tlDriver.get().get(getProp().getProperty(Constants.url));
			// Reporting
			getUtil().report(Constants.info, Constants.launchURL, getProp().getProperty(Constants.url), Constants.blank);
		 /*catch (MalformedURLException e) {
			// Reporting
			util.report(Constants.error, "<b> Driver: </b>"+browser, e.toString(), Constants.exception);
		} */
			return tlDriver.get();
	}

	public synchronized CommonUtil getUtil() {
		return util.get();
	}
	
	public synchronized Properties getProp() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(Constants.path_PropertyFile));
		} catch (FileNotFoundException e) {
			// Reporting
			getUtil().report(Constants.error, Constants.exception, e.toString(), Constants.exception);
		} catch (IOException e) {
			// Reporting
			getUtil().report(Constants.error, Constants.exception, e.toString(), Constants.exception);
		}
		return prop;
	}
	
	public Reporting getReportingInstance() {
		return new Reporting();
	}
	
	public MultiFunction getMultiFunctionInstance() {
		return new MultiFunction();
	}

}
