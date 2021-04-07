package framework.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class WeatherProperties {
	static final Logger logger = LogManager.getLogger(WeatherProperties.class);

	InputStream inputStream;
	Properties prop = new Properties();
	private static final WeatherProperties weatherProperties = new WeatherProperties();

	private WeatherProperties() {
		try {
			loadProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
	}

	public static WeatherProperties getInstance() {
		return weatherProperties;
	}

	private void loadProperties() throws IOException {

		try {
			String propFileName = System.getProperty("property_file");
			logger.info("Loading properties from file: " + propFileName);

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
				logger.info("Properties loaded successfully");

			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			inputStream.close();
		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public String getPropertyOrDefault(String key, String defaultValue) {
		return prop.getProperty(key) == null ? defaultValue : prop.getProperty(key);
	}

}
