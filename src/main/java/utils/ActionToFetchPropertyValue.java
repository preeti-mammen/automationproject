package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionToFetchValue.
 * 
 * @author Preeti Mammen
 */
public class ActionToFetchPropertyValue {
	
	/** The prop. */
	Properties prop = new Properties();
	
	/** The input. */
	InputStream input = null;
	
	/** The value. */
	String value = null;

	/**
	 * Action Method to fetch value from any property file.
	 *
	 * @param property
	 *            the property
	 * @param file
	 *            the file
	 * @return the string
	 */
	public String action(String property, String file) {

		try {
			input = new FileInputStream(file);
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

}
