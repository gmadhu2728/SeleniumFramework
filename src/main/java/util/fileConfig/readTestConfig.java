package util.fileConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class readTestConfig {
	static Properties propValue;

	public static void readFile() throws IOException {

		String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\Config\\testConfig.properties";
		FileReader file = new FileReader(filepath);

		propValue = new Properties();
		propValue.load(file);

	}

	public static String getPropertyValue(String propKey) throws IOException {

		String value = null;
		value = propValue.getProperty(propKey);
		return value;
	}
}
