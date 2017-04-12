
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

public class TC02GenerarEnvio {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	public static String curDir = System.getProperty("user.dir");

	MetodosReutilizables mr = new MetodosReutilizables();
	String nombreClase = getClass().getSimpleName();
	String nombreCarpeta = "ReporteResultados";

	ExtentReports report = new ExtentReports(curDir + "\\" + nombreCarpeta + "\\" + nombreClase + "\\WebTC02.html");
	ExtentTest logger;

	int tiempoC = 1000, tiempoM = 2500, tiempoL = 4000;

	String numeroFechaEnvio = "8";

	@BeforeMethod
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver",
				curDir + "\\src\\main\\resources\\drivers\\Mozilla\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "http://52.40.191.4:8080/cargo-tracker/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testTC02GenerarEnvio() throws Exception {

		try {
			logger = report.startTest("Test Cargo Tracker");

			logger.log(LogStatus.INFO, "Se lanzara browser con la web de Cargo Tracker");

			driver.get(baseUrl);
			Thread.sleep(tiempoM);

			driver.findElement(By.linkText("Administration Interface")).click();
			Thread.sleep(tiempoM);

			driver.findElement(By.linkText("Book")).click();
			Thread.sleep(tiempoL);

			try {
				if (driver.findElement(By.tagName("html")).getText().contains("Cargo Tracker")
						|| driver.findElement(By.cssSelector("span.titleText")).getText().contains("Cargo Tracker")) {
					File evidenciaTituloInicio = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					String pathEvidencia_TituloInicio = curDir + "\\" + nombreCarpeta + "\\" + nombreClase
							+ "\\Evidencias\\Titulo Form Inicio.png";
					FileUtils.copyFile(evidenciaTituloInicio, new File(pathEvidencia_TituloInicio));
					logger.log(LogStatus.INFO, logger.addScreenCapture(pathEvidencia_TituloInicio));
					logger.log(LogStatus.PASS,
							"Se detect&oacute correctamente Formulario Tras presionar opcion Administration Interface");
				} else {
					logger.log(LogStatus.FAIL,
							"No Se detect&oacute Formulario Tras presionar opcion Administration Interface");
				}
			} catch (Exception ex) {
				logger.log(LogStatus.ERROR, "Mensaje de error: " + ex.toString());
			}

			driver.findElement(By.xpath("//div[@id='j_idt29:origin']/div[3]/span")).click();
			driver.findElement(By.xpath("//div[@class='ui-selectonemenu-items-wrapper']//li[.='Hamburg (DEHAM)']"))
					.click();
			Thread.sleep(tiempoC);

			driver.findElement(By.id("j_idt29:j_idt36")).click();
			Thread.sleep(tiempoL);

			driver.findElement(By.xpath("//div[@id='j_idt29:destination']/div[3]/span")).click();
			driver.findElement(By.xpath("//div[@class='ui-selectonemenu-items-wrapper']//li[.='Melbourne (AUMEL) ']"))
					.click();
			Thread.sleep(tiempoC);

			driver.findElement(By.id("j_idt29:j_idt37")).click();
			Thread.sleep(tiempoL);

			driver.findElement(By.linkText("10")).click();
			Thread.sleep(tiempoL);

			driver.findElement(By.id("dateForm:bookBtn")).click();
			Thread.sleep(tiempoM);

			try {
				if (driver.findElement(By.tagName("html")).getText().contains("Not Routed")
						|| driver.findElement(By.xpath("//div[@id='mainDash:NotRouted_header']/span")).getText()
								.contains("Not Routed")) {
					File evidenciaFormEnvioCargo = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					String pathEvidencia_FormEnvioCargo = curDir + "\\" + nombreCarpeta + "\\" + nombreClase
							+ "\\Evidencias\\Formulario envio generado.png";
					FileUtils.copyFile(evidenciaFormEnvioCargo, new File(pathEvidencia_FormEnvioCargo));
					logger.log(LogStatus.INFO, logger.addScreenCapture(pathEvidencia_FormEnvioCargo));
					logger.log(LogStatus.PASS, "Se detect&oacute correctamente Formulario Tras generar envio");
				} else {
					logger.log(LogStatus.FAIL, "No Se detect&oacute Formulario Tras generar envio");
				}
			} catch (Exception ex) {
				logger.log(LogStatus.ERROR, "Mensaje de error: " + ex.toString());
			}

		} catch (Exception exx) {
			logger.log(LogStatus.ERROR, "Mensaje de error: " + exx.toString());
		}

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
