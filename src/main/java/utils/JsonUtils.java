package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtils.
 * 
 * @author Preeti Mammen
 */
public class JsonUtils {

	/**
	 * Generate string from json file.
	 *
	 * @param path
	 *            the path
	 * @return the string
	 */
	public static String generateStringFromResource(String path) {
		String prefJson = new JsonPath(new File(path)).using(new JsonPathConfig("ISO-8859-1")).prettify();
		return prefJson;
	}

	/**
	 * Update json key with string.
	 *
	 * @param jsonFile
	 *            the json file
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	@SuppressWarnings("unchecked")
	public static void updateJsonKeyWithString(String jsonFile, String key, String value) {
		JSONParser parser = new JSONParser();
		try {
			File fl = new File("src/test/resources/jsonFiles/" + jsonFile);
			FileReader fr = new FileReader(fl);
			Object object = parser.parse(fr);

			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;

			// Reading the String
			String assessmentId = (String) jsonObject.get(key);
			System.out.println("Old Value for KEY: " + key + " IS: " + assessmentId);

			// Updating
			jsonObject.put(key, value);

			String newassessmentId = (String) jsonObject.get(key);
			System.out.println("New Value for KEY: " + key + " IS: " + newassessmentId);

			if (fl.exists()) {
				fl.delete();
				fl.createNewFile();
			} else {
				fl.createNewFile();
			}

			// Writing Pretty Print JSON content into File
			FileWriter file = new FileWriter(fl);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jsonObject.toJSONString());
			file.write(gson.toJson(je));
			file.flush();
			file.close();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
