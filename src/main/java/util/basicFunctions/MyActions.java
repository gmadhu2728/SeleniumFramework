package util.basicFunctions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyActions extends Element {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	static JavascriptExecutor executor = null;

	public static void initialiseActions(WebDriver driver) {
		executor = (JavascriptExecutor) driver;
	}

	public void highlightPassElement(String element) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			executor.executeScript("arguments[0].style.border='3px solid green'", webElement);

			// Convert the locator to a string representation for storage
//			String locator = super.locateElement[0];
//			String locatorStr = locator.toString().replace("By.", "").split(": ")[1];
//
//			executor.executeScript("arguments[0].style.border='3px solid green';" + // Highlight the element
//					"localStorage.setItem('highlightedElement', '" + locatorStr + "');", webElement);
//
//			executor.executeScript("window.addEventListener('load', function() {"
//					+ " var highlightedElement = localStorage.getItem('highlightedElement');"
//					+ " if (highlightedElement) {" + " var element = document.querySelector(highlightedElement);"
//					+ " if (element) {" + " element.style.border = '3px solid green';" + // Reapply the border
//					" }" + " }" + "});");

		} catch (Exception e) {
			System.out.println("Unable to highlight the edited element");
		}
	}

	public void highlightFailElement(String element) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			executor.executeScript("arguments[0].style.border='3px solid red'", webElement);

		} catch (Exception e) {
			System.out.println("Unable to highlight the failed element");
		}
	}

	public void setText(String element, String textValue) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			webElement.clear();
			webElement.sendKeys(textValue);
			highlightPassElement(element);
		} catch (Exception e) {
			System.out.println("Unable to enter text in the element");
		}
	}

	public void setText_JS(String element, String textValue) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			executor.executeScript("arguments[0].value='" + textValue + "';", element);
		} catch (Exception e) {
			System.out.println("Unable to enter text in the element using JavaScript");
		}
	}

	public void enterTextIfBlank(String element, String textValue) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			String text = webElement.getText();
			if (text == null || text.equalsIgnoreCase("null")) {
				executor.executeScript("arguments[0].value='" + textValue + "';", element);
			}
		} catch (Exception e) {
			System.out.println("Unable to enter text in blank field");
		}
	}

	public void click(String element) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			webElement.click();
		} catch (Exception e) {
			System.out.println("Unable to click the element");
		}
	}

	public void jseClick(String element) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			System.out.println("Unable to click the element using JavaScript");
		}
	}

	public void selectByIndex(String element, int value) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.elementToBeSelected(webElement));
			Select select = new Select(webElement);
			select.selectByIndex(value);
		} catch (Exception e) {
			System.out.println("Unable to select element using index");
		}
	}

	public void selectByValue(String element, String value) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.elementToBeSelected(webElement));
			Select select = new Select(webElement);
			select.selectByValue(value);
		} catch (Exception e) {
			System.out.println("Unable to select element using value");
		}
	}

	public void selectByPartOfVisibleText(String element, String value) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.elementToBeSelected(webElement));
			Select select = new Select(webElement);
			List<WebElement> allOptions = select.getOptions();
			for (WebElement option : allOptions) {
				if (option.getText().toLowerCase().contains(value.toLowerCase())) {
					value = option.getText().trim();
					break;
				}
			}
			select.selectByVisibleText(value);
		} catch (Exception e) {
			System.out.println("Unable to select element using part of visible text");
		}
	}

	public void selectOptionIfBlank(String element) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.elementToBeSelected(webElement));
			Select select = new Select(webElement);
			String optionselected = select.getFirstSelectedOption().getText();
			if (optionselected == null || optionselected.equalsIgnoreCase("null")) {
				select.selectByIndex(1);
			}
		} catch (Exception e) {
			System.out.println("Unable to select value in blank dropdown");
		}
	}

	public void scrollToElement(String element) throws IOException {
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			executor.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			System.out.println("Unable to scroll the element using JavaScript");
		}
	}

	public String getAttribute(String element, String attribute) throws IOException {
		String value = null;
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			value = webElement.getAttribute(attribute);

		} catch (NoAlertPresentException e) {
			System.out.println("Unable to get attribute value");
		}
		return value;
	}

	public String getText(String element) throws IOException {
		String value = null;
		try {
			WebElement webElement = this.getElement(element);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			value = webElement.getText();
			highlightPassElement(element);

		} catch (NoAlertPresentException e) {
			System.out.println("Unable to get text value");
		}
		return value;
	}

	public void switchFrame(String nameOrID) throws IOException {
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
			driver.switchTo().frame(nameOrID);
		} catch (Exception e) {
			System.out.println("Unable to switch frame using Name or ID");
		}
	}

	public void switchFrame(WebElement element) throws IOException {
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
			driver.switchTo().frame(element);
		} catch (Exception e) {
			System.out.println("Unable to switch frame using webElement");
		}
	}

	public void switchToDefault(WebElement element) throws IOException {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("Unable to switch to default content");
		}
	}

	public void switchWindow() throws IOException {
		try {
			String parentWindow = driver.getWindowHandle();
			Set<String> allwindows = driver.getWindowHandles();
			wait.until(ExpectedConditions.numberOfWindowsToBe(allwindows.size()));

			Iterator<String> I1 = allwindows.iterator();
			while (I1.hasNext()) {
				String childWindow = I1.next();
				if (!(parentWindow.equals(childWindow))) {
					driver.switchTo().window(childWindow);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to switch window");
		}
	}

	public void switchWindow(String windowName) throws IOException {
		try {
			Set<String> allwindows = driver.getWindowHandles();
			Iterator<String> I1 = allwindows.iterator();
			wait.until(ExpectedConditions.numberOfWindowsToBe(allwindows.size()));

			while (I1.hasNext()) {
				String childWindow = I1.next();
				if ((driver.switchTo().window(childWindow).getTitle()).contains(windowName)) {
					driver.switchTo().window(childWindow);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to switch window using Window name");
		}
	}

	public boolean handleAlert() throws IOException {
		Boolean alert = false;
		try {
			driver.switchTo().alert().accept();
			alert = true;
		} catch (NoAlertPresentException e) {
			System.out.println("Unable to handle Alert");
		}
		return alert;
	}

	public void navigateToUrl(String url) throws IOException {
		try {
			driver.navigate().to(url);
			wait.until(ExpectedConditions.urlMatches(url));
		} catch (Exception e) {
			System.out.println("Unable to navigate to Url");
		}
	}

	public void closeWindow() throws IOException {
		try {
			driver.close();
		} catch (Exception e) {
			System.out.println("Unable to close the window");
		}
	}

	public int generateRandomNumbers(int no1, int no2) throws IOException {
		int randomNo = 0;
		try {
			Random r = new Random();
			randomNo = r.nextInt(no1, no2);
		} catch (Exception e) {
			System.out.println("Unable to generate random numbers");
		}
		return randomNo;
	}

	public String generateRandomAlphabets(int letterCount) throws IOException {
		String word = null;
		try {
			word = RandomStringUtils.randomAlphabetic(letterCount);
		} catch (Exception e) {
			System.out.println("Unable to generate random alphabets");
		}
		return word;
	}

	public String generateCurrentDate() throws IOException {
		String todayDate = null;
		try {
			Date d = new Date();
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
			todayDate = s.format(d);
			System.out.println(todayDate);
		} catch (Exception e) {
			System.out.println("Unable to generate current date");
		}
		return todayDate;
	}

	public String generateTodayDate() throws IOException {
		String todayDate = null;
		try {
			Date d = new Date();
			SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
			todayDate = s.format(d);
			System.out.println(todayDate);
		} catch (Exception e) {
			System.out.println("Unable to generate today date");
		}
		return todayDate;
	}

}
