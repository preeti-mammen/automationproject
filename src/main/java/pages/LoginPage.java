package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import utils.SetUpTest;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginPage.
 * 
 * @author Preeti Mammen
 */
public class LoginPage extends utils.ActionLibrary {

	/** The driver. */
	WebDriver driver;

	/**
	 * Instantiates a new login page.
	 *
	 * @param driver
	 *            the driver
	 */
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/** The User name text field. */
	@FindBy(id = "username")
	private WebElement UserNameTextField;

	/** The Password text field. */
	@FindBy(id = "password")
	private WebElement PasswordTextField;

	/** The Login button. */
	@FindBy(id = "Login")
	private WebElement loginButton;

	/**
	 * Login using.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	public void loginUsing(String username, String password) {
		waitForPageLoadComplete();
		SetUpTest.test.log(LogStatus.INFO, "Logging into Application");
		enterText(UserNameTextField, username);
		SetUpTest.test.log(LogStatus.PASS, "Entered UserName: " + username);
		enterText(PasswordTextField, password);
		SetUpTest.test.log(LogStatus.PASS, "Entered Password into the Application");
		clickElement(loginButton);
		SetUpTest.test.log(LogStatus.PASS, "Clicked Login Button");
		waitForPageLoadComplete();
		wait_now(10000);
		
	}
}
