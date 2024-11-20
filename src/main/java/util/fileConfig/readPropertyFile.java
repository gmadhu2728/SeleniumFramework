package util.fileConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class readPropertyFile {

	static Properties prop;

	public static void readFile() throws IOException {

		String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\DataResources\\ObjRepo.properties";
		FileReader file = new FileReader(filepath);

		prop = new Properties();
		prop.load(file);

	}

	public String[] getLocatorValue(String propKey) throws IOException {

		String[] value = null;
		propKey = prop.getProperty(propKey);
		if (propKey == null) {
			System.out.println("Element not found in object repository");
		} else {
			value = propKey.split(":");
		}
		return value;
	}

}
