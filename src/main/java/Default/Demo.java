package Default;

import java.io.IOException;
import org.testng.annotations.Test;

import common.driver.LaunchDriver;
import common.reporter.Reporter;
import common.webtest.webTest;
import util.basicFunctions.MyActions;

public class Demo extends webTest {

	@Test
	public void login() throws IOException {
		MyActions m = new MyActions();
		Reporter.startReport("TC1");
		LaunchDriver d = new LaunchDriver();
		Reporter.reportInfo("Enter username");
		m.setText_JS("username", "Admin");
		d.driver.navigate().refresh();
		String password = m.getText("text");
		System.out.println(password);
		d.driver.navigate().refresh();
		m.setText("password", "admin123");
		Reporter.insertScreenShot("Username");
		Reporter.reportPass("TC passed");
		Reporter.insertFullScreenShot("WebPage");
		
		
	}
}