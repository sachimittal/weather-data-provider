package framework.test_comparator;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class APITempProviderTest {

	static APITempProvider provider;
	
	@BeforeClass
	public static void init() {
		System.setProperty("property_file", "config.properties");
		provider = new APITempProvider();
	}

	@Test
	public void getTemperatureTestForExistingCity() {
		float puneTemp = provider.getTemperature("Pune", TempUnit.DEGREE);
		assertNotEquals(puneTemp, 0);
	}
	
	@Test
	public void getTemperatureTestForNonExistingCity() {
		float puneTemp = provider.getTemperature("Kharadi", TempUnit.DEGREE);
		assertNotEquals(puneTemp, 0);
	}
	
}
