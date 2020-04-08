package utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

// TODO: Auto-generated Javadoc
/**
 * The Class Constants.
 * 
 * @author Preeti Mammen
 */
public class Constants {

	/** The driver. */
	public static WebDriver driver;

	/** The get property. */
	public static GetProperty getProperty = new GetProperty();

	/** The report download path. */
	public static String propath = System.getProperty("user.dir");

	/** The report download path. */
	public static String reportDownloadPath = propath + "\\src\\main\\resources\\downloadedReports";

	/** The Constant browserName. */
	public static final String browserName = getProperty.getPropertyValue("BROWSER");

	/** The Constant ChromeDriverPath. */
	public static final String ChromeDriverPath = propath + "\\src\\main\\resources\\drivers\\chromedriver.exe";

	/** The Constant IEDriverPath. */
	public static final String IEDriverPath = propath + "\\src\\main\\resources\\drivers\\IEDriverServer.exe";

	/** The Constant GHECKODRIVERPATH. */
	public static final String GHECKODRIVERPATH = propath + "\\src\\main\\resources\\firefoxtools\\geckodriver.exe";

	/** Need to pass paramenters to maven Eg: clean verify install -Dusername= prmammen -Dpassword=test. */
	public static final String USERNAME = System.getProperty("username");

	/** The Constant password. */
	public static final String PASSWORD = System.getProperty("password");

	/** The Constant url. */
	public static final String URL = "https://test.salesforce.com";

	/**
	 * Method to get WebDriver instance for multiple browsers Cross browser
	 * implementation - Firefox > v55 (including gheckoDriver support), IE and
	 * Chrome.
	 *
	 * @param browserName
	 *            the browser name
	 * @return the driver
	 */
	public static WebDriver getDriver(String browserName) {
		if (browserName.equalsIgnoreCase("Firefox")) {
			DesiredCapabilities capabilities = setCapabilities(setProfile());
			FirefoxOptions options = new FirefoxOptions(capabilities);
			System.setProperty("webdriver.gecko.driver", GHECKODRIVERPATH);
			driver = new FirefoxDriver(options);
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", reportDownloadPath);
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("--disable-extensions");
			options.addArguments("incognito");
			caps.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
			driver = new ChromeDriver(options);
		} else {
			System.out.println("The browser you entered is incorrect. Please change to Firefox or Chrome.");
		}
		return driver;
	}

	/**
	 * To Create a Firefox Profile for Automation.
	 *
	 * @return the firefox profile
	 */
	public static final FirefoxProfile setProfile() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.privatebrowsing.autostart", true);
		profile.setPreference("browser.download.dir", reportDownloadPath);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"multipart/zip,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/octet-stream");
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.closeWhenDone", true);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
		profile.setPreference("pdfjs.disabled", true);
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);
		profile.setPreference("browser.tabs.remote.autostart", false);
	    profile.setPreference("browser.tabs.remote.autostart.1", false);
	    profile.setPreference("browser.tabs.remote.autostart.2", false);                   
	    profile.setPreference("browser.tabs.remote.force-enable", false);
		return profile;
	}

	/**
	 * Firefox Capabilities Set up.
	 *
	 * @param profile            the profile
	 * @return the desired capabilities
	 */
	public static final DesiredCapabilities setCapabilities(FirefoxProfile profile) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability("acceptInsecureCerts", true);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		return capabilities;
	}

	/**
	 * Gets the browser.
	 *
	 * @return the browser
	 */
	public static String getBrowser() {
		return browserName;
	}

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public static WebDriver getDriver() {
		return driver;
	}
}