package framework.test_comparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import framework.common.WeatherProperties;

public class APITempProvider implements TempDataProvider {

	private static final Logger logger = LogManager.getLogger(APITempProvider.class);

	@Override
	public float getTemperature(String location, TempUnit unit) {

		float temp = Float.NaN;
		try {
			URL url = new URL(getURL(location, unit));
			logger.info("API request:" + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = br.readLine();
			logger.info("API response:" + output);
			if (output != null) {
				JSONObject obj = new JSONObject(output);
				temp = obj.getJSONObject("main").getFloat("temp");
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			logger.error(e);
		} catch (ProtocolException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		return temp;
	}

	private String getURL(String location, TempUnit unit) {
		String weatherAPIUrl = WeatherProperties.getInstance().getProperty("weather.api.url");;
		switch (unit) {
		case DEGREE:
			return String.format(weatherAPIUrl, location, "metric");
		case FAHRENHEIT:
			return String.format(weatherAPIUrl, location, "imperial");
		default:
			return String.format(weatherAPIUrl, location, "metric");
		}
	}
}
