package framework.common;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class WeatherPropertiesTest {
	static WeatherProperties properties;

	@BeforeClass
	public static void init() {
		System.setProperty("property_file", "config.properties");
		properties = WeatherProperties.getInstance();
	}

	@Test
	public void testGetInstance() {
		assertNotNull(properties);
	}

	@Test
	public void testGetProperty() {
		assertTrue(properties.getProperty("driver.wait").equals("5"));
	}

	@Test
	public void testGetPropertyNonExistingKey() {
		assertNull(properties.getProperty("driver.waitnon-existing.key"));
	}

	@Test
	public void testGetPropertyOrDefault() {
		assertTrue(properties.getPropertyOrDefault("non-existing.key", "default").equals("default"));
	}

}
