package common.driver;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import util.fileConfig.readTestConfig;

public class LaunchDriver {

	public static WebDriver driver;

	public static void initialiseDriver() throws IOException {

		String browser = readTestConfig.getPropertyValue("Browser");
		String url = readTestConfig.getPropertyValue("URL");

		switch (browser.toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\chromedriver.exe");

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);

			break;

		case "edge":
			System.setProperty("webdriver.edge.driver",
					System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		}

		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	public static void closeUrl() {
//		driver.quit();
	}

}
