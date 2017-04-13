package com.tsoftlatam.automatizacion.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tsoftlatam.automatizacion.util.CommUtil;
import com.tsoftlatam.automatizacion.util.DriverNavegador;

public class AbstractTestObject {

	static ExtentReports extent;
	static ExtentTest test;
	String nombreTest;

	@BeforeClass
	public static void setup() {
		DriverNavegador.setupDriver();
		CommUtil.implicitWait(10);
		CommUtil.launchBrowser(DriverNavegador.WEB_SERVER);

		extent = new ExtentReports(DriverNavegador.CUR_DIR + "\\test-output\\extent_report\\index.html");
		extent.addSystemInfo("Browser", DriverNavegador.getBrowserAndVersion());
	}

	@BeforeMethod
	public void obtenerNombreDeLosMetodos(Method testMethod) {
		// obtener el nombre de los test
		// nombreTest = testMethod.getName();
		test = extent.startTest(testMethod.getName());
	}

	@AfterMethod
	public void deleteAllCookies() {
		DriverNavegador.driver.manage().deleteAllCookies();
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			// test.log(LogStatus.FAIL, "Test Case Failed - Las razones son " +
			// result.getName());
			test.log(LogStatus.FAIL, "Test Case Failed - Las razones son " + result.getThrowable());
			test.log(LogStatus.INFO, test.addScreenCapture(createScreenshot(DriverNavegador.driver)));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped is - Las razones son " + result.getName());
			test.log(LogStatus.INFO, test.addScreenCapture(createScreenshot(DriverNavegador.driver)));
		} else {
			test.log(LogStatus.PASS, "");
		}
		extent.endTest(test);
	}

	@AfterClass
	public static void tearDown() {
		extent.flush();
		extent.close();
		DriverNavegador.driver.quit();
	}

	public static String createScreenshot(WebDriver driver) {

		UUID uuid = UUID.randomUUID();
		// generate screenshot as a file object
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// copy file object to designated location
			FileUtils.copyFile(scrFile, new File(
					DriverNavegador.CUR_DIR + "\\test-output\\extent_report" + "\\evidencias\\" + uuid + ".png"));
		} catch (IOException e) {
			System.out.println("Error while generating screenshot:\n" + e.toString());
		}
		return "evidencias\\" + uuid + ".png";
	}

	public static void generarStepFail(String msgError, LogStatus status) {
		test.log(status, msgError);
		test.log(LogStatus.INFO, test.addScreenCapture(createScreenshot(DriverNavegador.driver)));
	}

	public static void generarStepExito(String msgExito) {
		test.log(LogStatus.PASS, msgExito);
	}

}
