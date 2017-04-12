package com.tsoftlatam.automatizacion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class IndexPage extends AbstractPageObject {

	public IndexPage(WebDriver driver) {
		super(driver);
	}

	// Atributos por medio del patron PAGE FACTORY
	@FindBy(how = How.XPATH, using = "html/body/div/div/div/h2[3]/a")
	WebElement lknAdministrationInterface;

	public boolean isElementPresent() {
		try {
			driver.findElement(By.className("intro-header"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public AdministrationInterfacePage clickAdministrationInterface() {
		lknAdministrationInterface.click();
		return new AdministrationInterfacePage(driver);
	}

	@Override
	public boolean isAlertPresent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeAlertAndGetItsText() {
		// TODO Auto-generated method stub
		return false;
	}
}
