package com.tsoftlatam.automatizacion.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
		extent = new ExtentReports(DriverNavegador.CUR_DIR + "\\extends_reports\\index.html");
		DriverNavegador.setupDriver();
		CommUtil.implicitWait(10);
		CommUtil.launchBrowser(DriverNavegador.WEB_SERVER);
	}

	@BeforeMethod
	public void obtenerNombreDeLosMetodos(Method testMethod) {
		// obtener el nombre de los test
		nombreTest = testMethod.getName();
	}

	@AfterMethod
	public void deleteAllCookies() {
		DriverNavegador.driver.manage().deleteAllCookies();
	}

	@AfterMethod
	public void closeTest() {
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
			FileUtils.copyFile(scrFile,
					new File(DriverNavegador.CUR_DIR + "\\extends_reports" + "\\evidencia\\" + uuid + ".png"));
		} catch (IOException e) {
			System.out.println("Error while generating screenshot:\n" + e.toString());
		}
		return "evidencia\\" + uuid + ".png";
	}

	public static void generarStepFail(String msgError, LogStatus status) {
		test.log(status, msgError);
		test.log(LogStatus.INFO, test.addScreenCapture(createScreenshot(DriverNavegador.driver)));
	}

	public static void generarStepExito(String msgExito) {
		test.log(LogStatus.PASS, msgExito);
	}

}
