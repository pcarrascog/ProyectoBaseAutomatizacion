package com.tsoftlatam.automatizacion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class AdministrationInterfacePage extends AbstractPageObject {

	public AdministrationInterfacePage(WebDriver driver) {
		super(driver);
	}

	// Patron page factory
	// @FindBy(how = How.ID, using = "body")
	// WebElement identificador;

	@Override
	public boolean isElementPresent() {
		try {
			driver.findElement(By.id("body"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
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
