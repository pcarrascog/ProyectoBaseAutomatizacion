package com.tsoftlatam.automatizacion.util;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverNavegador {

	static ArchivoProperties propiedades = new ArchivoProperties();
	static Properties properties = propiedades.getProperties("driver_navegador.properties");

	public static final String CUR_DIR = System.getProperty("user.dir");
	protected static final String DIR_DOWNLOAD = System.getProperty("user.home", "C:") + File.separator + "Downloads"
			+ File.separator;
	protected static final String BROWSER = System.getProperty("BROWSER", properties.getProperty("p_navegador"));
	public static final String WEB_SERVER = System.getProperty("WEB_SERVER", properties.getProperty("p_url"));

	public static WebDriver driver;
	// public static RemoteWebDriver driver;

	public static boolean setupDriver() {
		try {
			if (BROWSER.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						CUR_DIR + "\\src\\main\\resources\\drivers\\Mozilla\\geckodriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);

				driver = new FirefoxDriver(capabilities);
				// driver = new FirefoxDriver();

			} else if (BROWSER.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						CUR_DIR + "\\src\\main\\resources\\drivers\\ChromeDriver\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (BROWSER.equalsIgnoreCase("internetExplorer")) {
				System.setProperty("webdriver.ie.driver",
						CUR_DIR + "\\src\\main\\resources\\drivers\\IExplorerDriver\\IEDriverServer_x64.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				capabilities.setCapability("requireWindowFocus", true);
				driver = new InternetExplorerDriver(capabilities);
			} else if (BROWSER.equalsIgnoreCase("opera")) {
				System.setProperty("webdriver.opera.driver",
						CUR_DIR + "\\src\\main\\resources\\drivers\\Opera\\operadriver_64x.exe");
				driver = new OperaDriver();
			} else if (BROWSER.equalsIgnoreCase("phantom")) {
				System.setProperty("phantomjs.binary.path",
						CUR_DIR + "\\src\\main\\resources\\drivers\\Phantomjs\\phantomjs.exe");
				driver = new PhantomJSDriver();
			} else if (BROWSER.equalsIgnoreCase("jenkins")) {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
			} else {
				System.out.println("Tipo de Browser No Soportado");
				return false;
			}
			return true;
		} catch (Exception ex) {
			System.out.println("Error al ejecutar el Driver: " + ex.toString());
			return false;
		}
	}

}
