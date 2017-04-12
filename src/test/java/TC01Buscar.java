
import static org.testng.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TC01Buscar {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	public static String curDir = System.getProperty("user.dir");

	MetodosReutilizables mr = new MetodosReutilizables();
	String nombreClase = getClass().getSimpleName();
	String nombreCarpeta = "ReporteResultados";

	ExtentReports report = new ExtentReports(curDir + "\\" + nombreCarpeta + "\\" + nombreClase + "\\WebTC01.html");
	ExtentTest logger;

	int tiempoC = 1000, tiempoM = 2500, tiempoL = 4000;

	String idCargoBuscar = "35F5C71D";

	@BeforeMethod
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver",
				curDir + "\\src\\main\\resources\\drivers\\Mozilla\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "http://52.25.6.205:8080/cargo-tracker/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testTC01Buscar() throws Exception {

		logger = report.startTest("Test Cargo Tracker");

		logger.log(LogStatus.INFO, "Se lanzara browser con la web de Cargo Tracker");

		driver.get(baseUrl);
		driver.findElement(By.linkText("Public Tracking Interface")).click();
		Thread.sleep(tiempoM);

		driver.findElement(By.id("trackingForm:trackIdInput_input")).clear();
		driver.findElement(By.id("trackingForm:trackIdInput_input")).sendKeys(idCargoBuscar);
		Thread.sleep(tiempoC);

		driver.findElement(By.id("trackingForm:j_idt21")).click();
		Thread.sleep(tiempoM);

		try {
			if (driver.findElement(By.tagName("html")).getText().contains("Cargo " + idCargoBuscar)
					|| driver.findElement(By.cssSelector("p")).getText().contains("Cargo " + idCargoBuscar)) {
				File evidenciaBuscarCargo = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String pathEvidencia_BuscarCargo = curDir + "\\" + nombreCarpeta + "\\" + nombreClase
						+ "\\Evidencias\\Busqueda cargo.png";
				FileUtils.copyFile(evidenciaBuscarCargo, new File(pathEvidencia_BuscarCargo));
				logger.log(LogStatus.INFO, logger.addScreenCapture(pathEvidencia_BuscarCargo));
				logger.log(LogStatus.PASS, "Se detect&oacute correctamente Busqueda de Cargo");
			} else {
				logger.log(LogStatus.FAIL, "No Se detect&oacute Busqueda de Cargo");
			}
		} catch (Exception ex) {
			logger.log(LogStatus.ERROR, "Mensaje de error: " + ex.toString());
		}

		driver.findElement(By.xpath("//div[@id='mainCol2']/a/i")).click();
		Thread.sleep(tiempoM);

		report.endTest(logger);
		report.flush();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
