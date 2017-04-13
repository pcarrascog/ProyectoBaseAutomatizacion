package com.tsoftlatam.automatizacion.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.tsoftlatam.automatizacion.pages.AdministrationInterfacePage;
import com.tsoftlatam.automatizacion.pages.BookPage;
import com.tsoftlatam.automatizacion.pages.HeaderMenuPage;
import com.tsoftlatam.automatizacion.pages.IndexPage;
import com.tsoftlatam.automatizacion.util.DriverNavegador;

public class Example001Test extends AbstractTestObject {

	public Example001Test() {
		super();
	}

	@Test
	public void a_validaPaginaIndex() {
		try {
			test = extent.startTest(nombreTest, "Este método debe validar la entrada la página index.html del sitio");
			IndexPage index = new IndexPage(DriverNavegador.driver);
			if (index.isElementPresent()) {
				generarStepExito("index.hml identificado");
				Assert.assertTrue(true);
			} else {
				String msgError = "ERROR - No se encuentra en el index";
				generarStepFail(msgError, LogStatus.FAIL);
				Assert.fail(msgError);
			}
		} catch (Exception e) {
			generarStepFail("ERROR - " + e.getMessage(), LogStatus.ERROR);
		}
	}

	@Test
	public void b_clickAdministrationInterface() {
		try {
			test = extent.startTest(nombreTest,
					"Este método debe direccionar hacia el sitio 'Administration Interface'");
			IndexPage index = new IndexPage(DriverNavegador.driver);
			AdministrationInterfacePage aip = index.clickAdministrationInterface();
			Thread.sleep(4000);
			if (aip.isElementPresent()) {
				generarStepExito("Direccionado hacia 'Administrador Interface'");
				Assert.assertTrue(true);
			} else {
				String msgError = "ERROR - No ha sido direccionado hacia 'Administrador Interface'";
				generarStepFail(msgError, LogStatus.FAIL);
				Assert.fail(msgError);
			}
		} catch (Exception e) {
			generarStepFail("ERROR - " + e.getMessage(), LogStatus.ERROR);
		}
	}

	@Test
	public void c_clickLknBook() {
		try {
			test = extent.startTest(nombreTest, "Este método debe direccionar hacia el link 'Book'");

			// PASO 1 - Identificar el menu de la cabecera
			HeaderMenuPage hmp = new HeaderMenuPage(DriverNavegador.driver);
			Thread.sleep(2000);
			if (hmp.isElementPresent()) {
				generarStepExito("Se ha identificado el menú en la cabecera");
			} else {
				String msgError = "ERROR - no se ha desplegado el menu header para direccionar al link 'Book'";
				generarStepFail(msgError, LogStatus.FAIL);
				Assert.fail(msgError);
			}
			// PASO 2 - Identificar la página de Book
			BookPage bookpage = hmp.clickLknBook();
			Thread.sleep(4000);
			if (bookpage.isElementPresent()) {
				generarStepExito("Direccionado correctamente hacia la página 'Book'");
			} else {
				String msgError = "ERROR - No ha sido direccionado hacia la página 'Book'";
				generarStepFail(msgError, LogStatus.FAIL);
				Assert.fail(msgError);
			}
		} catch (Exception e) {
			generarStepFail("ERROR - " + e.getMessage(), LogStatus.ERROR);
		}
	}

	@Test
	public void d_seleccionarOpcionCmb() throws InterruptedException {
		try {
			test = extent.startTest(nombreTest, "Seleccionar una opción del comboBox desplegado");
			BookPage bookPage = new BookPage(DriverNavegador.driver);
			Thread.sleep(3000);
			if (bookPage.cmbSelectOption()) {
				generarStepExito("Seleccionado la opción > DALLAS");
				Assert.assertTrue(true);
			} else {
				String msgError = "ERROR - No ha sido encontrada la opcion seleccionada > DALLAS";
				generarStepFail(msgError, LogStatus.FAIL);
				Assert.fail(msgError);
			}
		} catch (Exception e) {
			generarStepFail("ERROR - " + e.getMessage(), LogStatus.ERROR);
		}
	}

	/**
	 * LO QUE NO SE DEBE HACER!!!!!!
	 */
	// @Test
	public void d_flujo() {
		IndexPage indexPage = new IndexPage(DriverNavegador.driver);
		if (indexPage.isElementPresent()) {
			AdministrationInterfacePage administrationInterfacePage = indexPage.clickAdministrationInterface();
			if (administrationInterfacePage.isElementPresent()) {
				HeaderMenuPage headerMenuPage = new HeaderMenuPage(DriverNavegador.driver);
				if (headerMenuPage.isElementPresent()) {
					BookPage bookPage = headerMenuPage.clickLknBook();
					if (bookPage.isElementPresent()) {
						// <demasiados if anidados promueven la mala lectura del
						// codigo>
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO: handle exception
						}
					} else {
						Assert.fail("ERROR - No es la pagina de 'Book'");
					}
				} else {
					Assert.fail("ERROR - No se encuentra el  menu 'Header Menu'");
				}
			} else {
				Assert.fail("ERROR - No es la pagina de 'Administrador Interface'");
			}
		} else {
			Assert.fail("ERROR - No es la pagina de index");
		}

	}

}
