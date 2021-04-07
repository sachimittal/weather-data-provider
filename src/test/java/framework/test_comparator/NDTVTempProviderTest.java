package framework.test_comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import framework.common.WeatherProperties;

public class NDTVTempProviderTest {

	static TempDataProvider provider;

	@BeforeClass
	public static void init() {
		System.out.println("Before called..");
		System.setProperty("property_file", "config.properties");
		System.setProperty("webdriver.chrome.driver", WeatherProperties.getInstance().getProperty("chrome.webdriver.path"));
		provider = new NDTVTempProvider();
	}

	@Test
	public void getTemperatureForExistingCityNotPinnedonMap() {
		float ndtvTemp = provider.getTemperature("Pune", TempUnit.DEGREE);
		assertTrue(ndtvTemp > 0);
	}

	@Test
	public void getTemperatureForExistingCityPinnedonMap() {
		float ndtvTemp = provider.getTemperature("Mumbai", TempUnit.DEGREE);
		assertTrue(ndtvTemp > 0);
	}

	@Test
	public void getTemperatureForNonExistingCityInList() {
		float ndtvTemp = provider.getTemperature("Agra", TempUnit.DEGREE);
		assertEquals(Float.NaN, ndtvTemp,0);
	}
	
	@Test
	public void getTemperatureForExistingCityPinnedonMapInFahrenheit() {
		float ndtvTemp = provider.getTemperature("Mumbai", TempUnit.FAHRENHEIT);
		assertTrue(ndtvTemp > 0);
	}

}
