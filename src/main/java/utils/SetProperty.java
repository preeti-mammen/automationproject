package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;
import org.apache.commons.codec.binary.Base64;

// TODO: Auto-generated Javadoc
/**
 * Run this file once for every new User It will create a 'config' property file
 * in src/main/resources with application params.
 * 
 * @author Preeti Mammen
 **/
public class SetProperty {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		Properties prop = new Properties();
		OutputStream output = null;
		String Browser, URL, userName, origPassword;

		try {
			output = new FileOutputStream("src/main/resources/properties/config.properties");
			Scanner in = new Scanner(System.in);

			// Read values from User
			System.out.println(
					"Enter the browser on which you want to run the test. Accepatable values are FirefoxPortable, FIREFOX, IE and Chrome:");
			Browser = in.nextLine();

			System.out.println("Enter the URL of the application:");
			URL = in.nextLine();

			System.out.println("Enter the username:");
			userName = in.nextLine();

			// Encode the password value
			System.out.println("Enter the password");
			origPassword = in.nextLine();
			byte[] encoded = Base64.encodeBase64(origPassword.getBytes());
			System.out.println("Original Password String: " + origPassword);
			System.out.println("Encoded Password String: " + new String(encoded));

			// Set Property Values
			prop.setProperty("BROWSER", Browser);
			prop.setProperty("APP_URL", URL);
			prop.setProperty("USERNAME", userName);
			prop.setProperty("PASSWORD", String(encoded));

			// Add values to Driver Path for Chrome and IE browsers
			prop.setProperty("CHROME_DRIVER_PATH",
					System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver.exe");
			prop.setProperty("IE_DRIVER_PATH",
					System.getProperty("user.dir") + "/src/main/resources/drivers/IEDriverServer.exe");
			prop.setProperty("FIREFOX_PORTABLE_PATH", System.getProperty("user.dir")
					+ "/src/main/resources/firefoxtools/FirefoxPortable/FirefoxPortable.exe");

			// Save Properties to Project Root folder
			prop.store(output, null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}

	}

	/**
	 * String.
	 *
	 * @param encoded
	 *            the encoded
	 * @return the string
	 */
	private static String String(byte[] encoded) {
		String s = new String(encoded);
		return s;
	}

}
