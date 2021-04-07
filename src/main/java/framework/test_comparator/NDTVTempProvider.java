package framework.test_comparator;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import framework.common.WeatherProperties;

public class NDTVTempProvider implements TempDataProvider {

	private static Logger logger = LogManager.getLogger(NDTVTempProvider.class);

	@Override
	public float getTemperature(String location, TempUnit unit) {
		String temperature;
		try {
			temperature = navigateAndFetchTemperature(location, unit);
			return Float.parseFloat(temperature);
		} catch (NumberFormatException ex) {
			logger.error(ex);
		}

		return Float.NaN;
	}

	private String navigateAndFetchTemperature(String location, TempUnit unit) {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(
				Long.parseLong(WeatherProperties.getInstance().getPropertyOrDefault("driver.wait", "5")),
				TimeUnit.SECONDS);
		String url = WeatherProperties.getInstance().getProperty("ndtv.url");
		try {
			String temp = "";
			driver.get(url);

			if (driver.findElements(By.className("notnow")).size() > 0)
				driver.findElement(By.linkText("No Thanks")).click();

			driver.findElement(By.id("h_sub_menu")).click();
			driver.findElement(By.linkText("WEATHER")).click();
			driver.findElement(By.id("searchBox")).sendKeys(location);
			if (driver.findElements(By.xpath(String.format("//div[@class='message']//label[@for='%s']", location)))
					.isEmpty())
				return "No data available for the passed location";

			WebElement cityCheckBox = driver.findElement(By.id(location));

			if (cityCheckBox.isSelected() == true)
				logger.info(location + " already present on map");
			else
				driver.findElement(By.id(location)).click();

			driver.findElement(By.xpath(String.format("//div[@title='%s']", location))).click();

			switch (unit) {
			case FAHRENHEIT:
				temp = driver
						.findElement(By.xpath(
								String.format("//div[@title='%s']//div//span[@class='tempWhiteText']", location)))
						.getText();
				break;
			case DEGREE:
				temp = driver
						.findElement(By
								.xpath(String.format("//div[@title='%s']//div//span[@class='tempRedText']", location)))
						.getText();
				break;
			}

			return temp.substring(0, temp.length() - 1);

		} catch (NoSuchElementException ex) {
			logger.error(ex);
		} finally {
			logger.info("Closing the web driver");
			driver.close();
		}
		return "";
	}

}
