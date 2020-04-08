package utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class ScreenShotOnFailure.
 * 
 * @author Preeti Mammen
 */
public class ScreenShotOnFailure extends TestListenerAdapter {

	/**
	 * Method to take screenshot on test failure.
	 *
	 * @param tr
	 *            the tr
	 */
	@Override
	public void onTestFailure(ITestResult tr) {
		WebDriver driver = Constants.getDriver();
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = "screenshots/failed";
		new File(destDir).mkdir();
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			FileUtils.copyFile(srcFile, new File(destDir + "/" + destFile));
		} catch (IOException io) {
			io.printStackTrace();
		}
		Reporter.setCurrentTestResult(tr);
		Reporter.log("Saved <a href=../screenshots/" + destFile + ">Screenshot</a>");
	}

}
