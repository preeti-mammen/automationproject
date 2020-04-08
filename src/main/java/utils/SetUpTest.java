package utils;

import java.io.File;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import pages.LoginPage;
import utils.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utils.ActionLibrary;

// TODO: Auto-generated Javadoc
/**
 * The Class SetUpTest.
 * 
 * @author Preeti Mammen
 */
public class SetUpTest {

	/** The act. */
	public static ActionLibrary act = new ActionLibrary();

	/** The login page. */
	public static LoginPage loginPage;

	/** The extent report file. */
	public static String extentReportFile = System.getProperty("user.dir") + "/extentreports/extentreports.html";

	/** The config file path. */
	public static String configFilePath = System.getProperty("user.dir") + "/extentreports/extent-config.xml";

	/** The extent. */
	public static ExtentReports extent = new ExtentReports(extentReportFile, true);

	/** The test. */
	public static ExtentTest test;

	/**
	 * Before class config.
	 */
	@BeforeClass
	public void setUp() {

		try {
			// Set up for Extent Reports
			extent.addSystemInfo("Environment", "Salesforce-AT");
			extent.loadConfig(new File(configFilePath));

			// Launch the Browser
			Constants.getDriver(Constants.getBrowser());
			// Launch Application
			Constants.getDriver().manage().deleteAllCookies();
			Constants.getDriver().get(Constants.URL);
			Capabilities capability = ((RemoteWebDriver) Constants.getDriver()).getCapabilities();
			String browserName = capability.getBrowserName();
			String browserVersion = capability.getVersion();
			extent.addSystemInfo("Browser Name", browserName).addSystemInfo("Browser Version", browserVersion);
			enablePages();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Exception Occurred: " + e.getMessage());
		}
	}

	/**
	 * Enable pages.
	 */
	private void enablePages() {
		Reporter.log("Inside Method for Enabling Page classes");
		loginPage = new LoginPage(Constants.getDriver());
	}

	/**
	 * After class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public void tearDown() throws Exception {
		try {
			Constants.getDriver().close();
			Constants.getDriver().quit();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Exception Occurred: " + e.getMessage());
		}
	}
}
