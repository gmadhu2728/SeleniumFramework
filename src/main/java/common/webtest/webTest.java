package common.webtest;

import java.io.IOException;
import org.testng.annotations.*;

import com.aventstack.extentreports.model.Report;

import common.driver.LaunchDriver;
import common.reporter.Reporter;
import util.basicFunctions.MyActions;
import util.fileConfig.readPropertyFile;
import util.fileConfig.readTestConfig;

public class webTest {
	@BeforeTest
	public void initialise() throws IOException {
		readPropertyFile.readFile();
		readTestConfig.readFile();

		LaunchDriver.initialiseDriver();
		Reporter.getReportInstance();
		MyActions.initialiseActions(LaunchDriver.driver);
	}

	@AfterTest
	public void finalise() throws IOException {
		LaunchDriver.closeUrl();
		Reporter.finishReport();
	}

}
