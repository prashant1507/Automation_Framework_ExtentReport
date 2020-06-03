package com.demo.resources;

public class Constants {

	public static final String userDir = System.getProperty("user.dir");
	public static final String driver_Chrome="webdriver.chrome.driver"; 
	public static final String driver_Firefox="webdriver.gecko.driver";
	public static final String driver_IE="webdriver.ie.driver";
	public static final String path_ChromeDriver = userDir + "/Resources/driver/chromedriver_2.42.exe";
	public static final String path_FirefoxDriver = userDir + "/Resources/driver/geckodriver_v0.23.0x64.exe";
	public static final String path_InternetExplorerDriver = userDir + "/Resources/driver/IEDriverServer_v3.14.0_x64.exe";
	public static final String path_PropertyFile = userDir + "/Resources/property.properties";
	public static final String path_vmWare = userDir + "/Resources/vmware.bat";
	public static final String command_vmClose="taskkill -f /IM vmware.exe";
	public static final String testerName = "TesterName";
	public static final String dateTime = "yyyy_MM_dd_hh_mm_ss_SSS";
	public static final String date = "dd_MM_yyyy";
	public static final String blank = "";
	public static final String singleSpace = " ";
	public static final String lineChangeCharacter = "\n";
	public static final String url = "Url";
	public static final String environment = "Env";
	public static final String htmlBreak = "<br>";
	public static final String browser = "Browser";
	
	// Execution Status
	public static final String fail = "Fail";
	public static final String pass = "Pass";
	public static final String info = "Info";
	public static final String error = "Error";
	public static final String skip = "Skip";
	
	
	// Execution Message
	public static final String closeVMWare = ": VMware Closed Successfully";
	public static final String elementNotFound = "<b>Element not found: </b>";
	public static final String element = "<b>Element: </b>";
	public static final String resultMatched = "<b>Result matched</b>";
	public static final String resultNotMatched = "<b>Result not matched</b>";
	public static final String actual = "<br><b>Actual: </b>";
	public static final String expected = "<br><b>Expected: </b>";
	public static final String exception = "<b>Exception: </b>";
	public static final String keySend = "</br><b>Keys Send: </b>";
	public static final String isClicked = "is Clicked";
	public static final String textPresent = "<br><b>Text Present as </b>";
	public static final String elementDisplayed = "<br><b>Element Displayed: </b>";
	public static final String elementEnabled = "<br><b>Element Enabled: </b>";
	public static final String elementSelected = "<br><b>Element Selected: </b>";
	public static final String optionSelected = "<br><b>Option Selected: </b>";
	public static final String launchURL = "<b>URL: <b>";
	public static final String reason = "<br><b>Reason: </b>";
	public static final String elementNotDisplayed = "<b>Element not displayed: </b>";
	public static final String elementNotEnabled = "<b>Element not enabled: </b>";
	public static final String elementNotSelected = "<b>Element not selected: </b>";
	public static final String optionNotAvailable = "<b>Option not available: </b>";
	
	//vmware
	public static final String vmWare = "VMWare";
	public static final String esx = "esx";
	public static final String ws = "ws";
	public static final String fusion = "fusion";
	public static final String vmurl = "vmurl";
	public static final String snapShotNotExist = "Error: The snapshot does not exist";
	public static final String fileNotExist = "The file does not exist.";
	public static final String fileNotFound = "Error: A file was not found";
	public static final String virtualMachineNotExist = "The virtual machine cannot be found";
	public static final String urlNotLaunched = "<b>Url not launched successfully!!</b>";
	public static final String terminated = "<b>Terminated due to error!!</b>";
	public static final String fileAlreadyInUse = "Error: The file is already in use";
	public static final String invalidLogin = "Error: Invalid user name or password for the guest OS";
	
}
