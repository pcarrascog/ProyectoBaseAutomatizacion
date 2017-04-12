package com.tsoftlatam.automatizacion.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPageObject {
	WebDriver driver;

	public AbstractPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public abstract boolean isElementPresent();

	public abstract boolean isAlertPresent();

	public abstract boolean closeAlertAndGetItsText();
}
