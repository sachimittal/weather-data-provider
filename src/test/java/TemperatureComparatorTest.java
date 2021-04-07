
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import framework.common.WeatherProperties;
import framework.test_comparator.APITempProvider;
import framework.test_comparator.NDTVTempProvider;
import framework.test_comparator.TempDataProvider;
import framework.test_comparator.TempUnit;

public class TemperatureComparatorTest {

	@BeforeClass
	public static void setSystemProperty() {
		System.setProperty("property_file", "config.properties");
		System.setProperty("webdriver.chrome.driver", WeatherProperties.getInstance().getProperty("chrome.webdriver.path"));
	}

	@Test
	public void tempFromBothSourcesInDegreeShouldBeSame() {
		TempDataProvider provider = new NDTVTempProvider();
		float ndtvTemp = provider.getTemperature("Bengaluru", TempUnit.DEGREE);

		provider = new APITempProvider();
		float apiTemp = provider.getTemperature("Bengaluru", TempUnit.DEGREE);
		String resultMessage = String.format("Temperature from NDTV %s and API %s", ndtvTemp, apiTemp);
		System.out.println(resultMessage);

		assertEquals(resultMessage, ndtvTemp, apiTemp, 2);
	}

	@Test
	public void tempFromBothSourcesInFahrenheitShouldBeSame() {
		TempDataProvider provider = new NDTVTempProvider();
		float ndtvTemp = provider.getTemperature("Bengaluru", TempUnit.FAHRENHEIT);

		provider = new APITempProvider();
		float apiTemp = provider.getTemperature("Bengaluru", TempUnit.FAHRENHEIT);
		String resultMessage = String.format("Temperature from NDTV %s and API %s", ndtvTemp, apiTemp);
		System.out.println(resultMessage);

		assertEquals(resultMessage, ndtvTemp, apiTemp, 2);
	}

}
