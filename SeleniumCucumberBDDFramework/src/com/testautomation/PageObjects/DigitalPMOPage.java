package com.testautomation.PageObjects;

import org.openqa.selenium.WebDriver;


public class DigitalPMOPage extends BasePage{
	
	public String getTitle(WebDriver driver) throws Exception
	{
		Thread.sleep(2000);
		return driver.getTitle();
	}

}
