package util.basicFunctions;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import common.driver.LaunchDriver;
import util.fileConfig.readPropertyFile;

public class Element extends readPropertyFile {

	WebDriver driver = null;
	String[] locateElement = null;

	public WebElement getElement(String element) throws IOException {

		WebElement webElement = null;
		driver = LaunchDriver.driver;

		locateElement = getLocatorValue(element);
		String[] elementLocators = locateElement[1].split("&");
		int locatorSize = elementLocators.length;

		int i = 0;
		boolean elementCheck = false;

		while (!elementCheck) {
			try {
				switch (locateElement[0]) {
				case "name":
					webElement = driver.findElement(By.name(elementLocators[i].trim()));
					elementCheck = true;
					break;

				case "id":
					webElement = driver.findElement(By.id(elementLocators[i].trim()));
					elementCheck = true;
					break;

				case "xpath":
					webElement = driver.findElement(By.xpath(elementLocators[i].trim()));
					elementCheck = true;
					break;

				case "cssSelector":
					webElement = driver.findElement(By.cssSelector(elementLocators[i].trim()));
					elementCheck = true;
					break;

				case "className":
					webElement = driver.findElement(By.className(elementLocators[i].trim()));
					elementCheck = true;
					break;
				}
			} catch (NoSuchElementException e) {
				if (locatorSize == 1) {
					System.out.println("Unable to locate Element");
					elementCheck = true;
				} else {
					i++;
				}
			}
		}

		return webElement;
	}
}
