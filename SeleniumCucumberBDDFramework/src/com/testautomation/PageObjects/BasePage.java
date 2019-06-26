package com.testautomation.PageObjects;

import org.openqa.selenium.WebDriver;

import com.testautomation.Utility.CommonLib;

public class BasePage {
	
	CommonLib commonLib = new CommonLib();

	public void launchBrowser(String url, WebDriver driver) {
		
		driver.get(url);
		driver.manage().window().maximize();		
		
	}
	
	public void closeBrowser(WebDriver driver) throws Throwable {
		
		commonLib.closeBrowser(driver);
		
	}


}
