package com.testautomation.StepDef;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;

import com.testautomation.Listeners.ExtentReportListener;
import com.testautomation.PageObjects.DigitalPMOPage;
import com.testautomation.Utility.CommonLib;
import com.testautomation.Utility.PropertiesFileReader;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DigitalPMOStepDefinition extends ExtentReportListener {

	DigitalPMOPage digitalPMOPage = new DigitalPMOPage();	
	public WebDriver driver = new CommonLib().getDriver();
	PropertiesFileReader obj= new PropertiesFileReader();
	ExtentTest logInfo=null;
		
	@Before
    public void beforeScenario(Scenario scenario) {
		test =  extent.createTest(scenario.getName());
		test =  test.createNode(scenario.getName());
    }
	
	@Given("^Open Digital PMO portal$")
	public void openDigitalPMO() throws IOException {

		Properties properties = obj.getProperty();

		try {
			
		    logInfo = test.createNode(new GherkinKeyword("Given"), "Open Digital PMO portal");
			digitalPMOPage.launchBrowser(properties.getProperty("APP_URL"), driver);
			logInfo.pass("Successfully open Digital PMO Portal");
			logInfo.addScreenCaptureFromPath(captureScreenShot(driver));

		} catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, logInfo, e);
		}
	}

	@When("^Verify Digital PMO home page is open$")	
	public void verifyIsAtDigitalPMO() throws IOException {
		
		Properties properties=obj.getProperty();
		
	try {
									
			logInfo=test.createNode(new GherkinKeyword("When"), "Verify Digital PMO home page is open");
			String expectedDigitalPMOTitle= properties.getProperty("digitalpmo.page.title");
			String actualDigitalPMOTitle= digitalPMOPage.getTitle(driver);
			Assert.assertEquals(actualDigitalPMOTitle, expectedDigitalPMOTitle,"Digital PMO Title is not matching");		
			logInfo.pass("Digital PMO Home Page title Verification is successful");
			logInfo.addScreenCaptureFromPath(captureScreenShot(driver));	
		
		} catch (AssertionError | Exception e) {
			testStepHandle("FAIL",driver,logInfo,e);			
		}	
	}

	@Then("^Verify content on Home page$")
	public void verifyPageContent() {

	}

	@Then("^Validate backlog Status Report table$")
	public void validateBacklogStatusReport() {

	}

	@Then("^Close the browser$")
	public void closeDigitalPMO() {
		
		  try { digitalPMOPage.closeBrowser(driver); 
		  	  } catch (Throwable e) { 
			  // TODO Auto-generated catch block 
			  e.printStackTrace(); }
		 

	}

}
