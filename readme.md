# Project Title

Weather Reporting Comparator

## Getting Started
This framework provides the weather information from multiple sources. Main Interfaces and classes are listed below

TempDataProvider Interface {
	float getTemperature(String location, TempUnit unit)
}

Interface have 2 concrete implementations
1. NDTVTempProvider - Reads the temperature information from NDTV website's weather page
2. APITempProvider - Provided the data from http://api.openweathermap.org 

### Prerequisites

Java 8 or above <br/>
Maven <br/>
Junit <br/>
Selenium <br/>
ChromeDriver - Chrome driver is different than chroe browser. Download if not present from https://chromedriver.chromium.org/downloads


## Running the tests
Do remember to change the ChromeDriver Path in the config file.


## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Author

* **Sachi Mittal** 

