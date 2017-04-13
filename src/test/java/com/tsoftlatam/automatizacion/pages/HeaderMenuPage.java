package com.tsoftlatam.automatizacion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeaderMenuPage extends AbstractPageObject {

	public HeaderMenuPage(WebDriver driver) {
		super(driver);
	}

	// Atributos por medio del patron PAGE FACTORY
	@FindBy(how = How.XPATH, using = ".//*[@id='j_idt15']/a[2]")
	WebElement lknBookMenu;

	public boolean isElementPresent() {
		try {
			driver.findElement(By.xpath(".//*[@id='j_idt15']"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public BookPage clickLknBook() {
		lknBookMenu.click();
		return new BookPage(driver);
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
