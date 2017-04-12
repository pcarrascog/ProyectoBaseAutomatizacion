package com.tsoftlatam.automatizacion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BookPage extends AbstractPageObject {

	public BookPage(WebDriver driver) {
		super(driver);
	}

	// Atributos por medio del patron PAGE FACTORY
	@FindBy(how = How.XPATH, using = "//div[@id='j_idt29:origin']/div[3]/span")
	WebElement comboBox;

	@FindBy(how = How.XPATH, using = "//div[@id='j_idt29:origin_panel']/div/ul/li[2]")
	WebElement comboOrigen;

	public boolean isElementPresent() {
		try {
			driver.findElement(By.id("j_idt29"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean cmbSelectOption() {
		try {
			comboBox.click();
			comboOrigen.click();
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
