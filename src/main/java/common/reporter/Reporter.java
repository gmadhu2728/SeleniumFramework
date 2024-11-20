package common.reporter;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import common.driver.LaunchDriver;
import ru.yandex.qatools.ashot.*;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import util.basicFunctions.Element;

public class Reporter {
	public static ExtentReports report;
	public static ExtentTest logReport = null;
	public static WebDriver driver = null;
	static Element SCelement = new Element();

	public static ExtentReports getReportInstance() {
		try {
			driver = LaunchDriver.driver;

			if (report == null) {
				String reportName = "FinalReport.html";
				ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
						System.getProperty("user.dir") + "/TestOutput/" + reportName);
				report = new ExtentReports();
				report.attachReporter(sparkReporter);

				report.setSystemInfo("OS", "Windows 10");
				report.setSystemInfo("Environment", "UAT");
				report.setSystemInfo("Build Number", "10.1.5");
				report.setSystemInfo("Browser", "chrome");

				sparkReporter.config().setDocumentTitle("Automation Test Results");
				sparkReporter.config().setReportName("Test Report on OrangeHRM page");
				sparkReporter.config().setTheme(Theme.DARK);
				sparkReporter.config().setTimeStampFormat("MMM dd,yyyy HH:mm:ss");
			}

		} catch (Exception e) {

		}
		return report;
	}

	public static void startReport(String TCname) {
		logReport = report.createTest(TCname);
	}

	public static void reportPass(String pass) {
		logReport.log(Status.PASS, pass);
	}

	public static void reportFail(String fail) {
		logReport.log(Status.FAIL, fail);
	}

	public static void reportInfo(String info) {
		logReport.log(Status.INFO, info);
	}

	public static void insertScreenShot(String SCdescription) {
		String screenshot = takeSC();
		try {
			logReport.addScreenCaptureFromBase64String(SCdescription, screenshot);
//			logReport.info(SCdescription, MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String takeSC() {

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		String sourceFile = takeScreenshot.getScreenshotAs(OutputType.BASE64);
//		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
//		screenShotFile = System.getProperty("user.dir") + "/ScreenShots/" + screenShotFile + ".jpg";
//		File destFile = new File(screenShotFile);

//		try {
//			FileUtils.copyFile(sourceFile, destFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return sourceFile;
	}

	public static void insertFullScreenShot(String SCdescription) throws IOException {
		String screenshot = takeFullSC(SCdescription);
		try {
			logReport.info(SCdescription, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String takeFullSC(String screenShotFile) throws IOException {
		screenShotFile = System.getProperty("user.dir") + "/ScreenShots/" + screenShotFile + ".jpg";
		try {
//			Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
//					.takeScreenshot(driver);
//			ImageIO.write(s.getImage(), "png", new File(System.getProperty("user.dir") + dateFormat + ".png"));

			Object devicePixelRatio = ((JavascriptExecutor) driver).executeScript("return window.devicePixelRatio");
			String dprValue = String.valueOf(devicePixelRatio);
			float windowDPR = Float.parseFloat(dprValue);

			Screenshot screenshot = new AShot()
					.shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(windowDPR), 1000))
					.takeScreenshot(driver);
			ImageIO.write(screenshot.getImage(), "png", new File(screenShotFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenShotFile;
	}

	public static void finishReport() {
		report.flush();
	}
}
