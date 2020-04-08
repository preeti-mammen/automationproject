package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProperty.
 * 
 * @author Preeti Mammen
 */
public class GetProperty {

	/**
	 * Method to get value from config property file in src/main/resources.
	 *
	 * @param property
	 *            the property
	 * @return the property value
	 */
	public String getPropertyValue(String property) {
		Properties prop = new Properties();
		InputStream input = null;
		String value = null;

		try {
			input = new FileInputStream("src/main/resources/properties/config.properties");

			// Load Properties file
			prop.load(input);

			if (property.equalsIgnoreCase("PASSWORD")) {
				byte[] decoded = Base64.decodeBase64(prop.getProperty(property).getBytes());
				value = String(decoded);
			} else {
				value = prop.getProperty(property);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}

		return value;

	}
	
	public static String getPropertyValue(String property, String fileName) {
		Properties prop = new Properties();
		InputStream input = null;
		String value = null;

		try {
			input = new FileInputStream("src/test/resources/properties/" + fileName);

			// Load Properties file
			prop.load(input);
			value = prop.getProperty(property);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}

		return value;
	}

	/**
	 * String.
	 *
	 * @param decoded
	 *            the decoded
	 * @return the string
	 */
	private String String(byte[] decoded) {
		String s = new String(decoded);
		return s;
	}

}
