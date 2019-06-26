package com.testautomation.Utility;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.akiban.sql.parser.FetchStatementNode;

public class CommonLib {
	
	public WebDriver webDriver = null;
	final static Logger logger = Logger.getLogger(CommonLib.class);
	List<WebElement> webElement =null;
	WebElement element = null;
	Select dropdown = null;
	static PropertiesFileReader obj= new PropertiesFileReader();
	static Properties properties = null;
	
	public WebDriver getDriver(){
	
		try {
			properties = obj.getProperty();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		
		try {
			
			if (properties.getProperty("TARGET_BROWSER").equalsIgnoreCase("chrome")) {

				System.out.println("Into DESKTOP CHROME Browser");
				System.setProperty("webdriver.chrome.driver",
						properties.getProperty("CHROME_DRIVER_PATH") + "/chromedriver.exe");
				webDriver = new ChromeDriver();
				webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

			} else if (properties.getProperty("TARGET_BROWSER").equalsIgnoreCase("ie")) {

				System.out.println("Into DESKTOP IE Browser");

				System.setProperty("webdriver.ie.driver",
						properties.getProperty("IE_DRIVER_PATH") + "/IEDriverServer.exe");
				webDriver = new InternetExplorerDriver();

				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability("nativeEvents", false);
				capabilities.setCapability("unexpectedAlertBehaviour", "accept");
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				capabilities.setCapability("disable-popup-blocking", true);
				capabilities.setCapability("enablePersistentHover", true);
				capabilities.setCapability("ignoreZoomSetting", true);
				webDriver = new InternetExplorerDriver(capabilities);
				webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

			} else {

				System.out.println("Into DESKTOP FIREFOX Browser");
				System.setProperty("webdriver.gecko.driver",
						properties.getProperty("FIREFOX_DRIVER_PATH") + "/geckodriver.exe");
				FirefoxProfile profile = new FirefoxProfile();
				profile.setAcceptUntrustedCertificates(true);
				profile.setAssumeUntrustedCertificateIssuer(false);
//			webDriver = new FirefoxDriver(profile); 
				webDriver = new FirefoxDriver();
				webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return webDriver;

	}

	public static String getCurrentTime() {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return Long.toString(timestamp.getTime());
	}

	public void click(String objectref, WebDriver driver, String methodType) throws Throwable {
		try {

			Thread.sleep(500);
			
			System.out.println("Into Click() :: Object Reference Is:" + objectref);
			logger.info("Clicking on Webelement: " + objectref);

			logger.info("...................Click Event Started........");
			if(methodType.equalsIgnoreCase("xpath")) {
				driver.findElement(By.xpath(objectref)).click();
			}else if(methodType.equalsIgnoreCase("id")) {
				driver.findElement(By.id(objectref)).click();
			}
			logger.info("...............Click Event Completed........");

			// captureScreenShot(driver);
			Thread.sleep(100);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Not able to Click --- " + e.getMessage());

		}
	}

	// This is implicit wait
	public void wait(WebDriver driver) throws Throwable

	{

		try {
			logger.info("Wait for 10 seconds");
			Thread.sleep(500);
			System.out.println("Into wait() :: Driver Is:" + driver);
			
			System.out.println("...................wait Started........");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			System.out.println("...................wait Completed........");

		} catch (Exception e) {
			logger.error("Not able to Wait --- " + e.getMessage());

		}
	}

	// This is Explicit wait
	public void waitFor(String objectref, WebDriver driver, String methodType) throws Throwable {
		try {
			logger.info("Wait for the object to appear");

			Thread.sleep(500);
			System.out.println("Into waitFor() :: Driver Is:" + driver);
			System.out.println("Into waitFor() :: Object Reference Is:" + objectref);

			System.out.println("...................wait Started........");

			WebDriverWait wait = new WebDriverWait(driver, 60);

			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));;
			if(methodType.equalsIgnoreCase("xpath")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(objectref)));
			}else if(methodType.equalsIgnoreCase("id")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id(objectref)));
			}
			
			System.out.println("...................wait Completed........");

		} catch (Exception e) {
			logger.error("Not able to Wait --- " + e.getMessage());

		}
	}
	
	public void validatePopup(String objectref, WebDriver driver, String inputparam, String methodType) throws Throwable {
		try {
			logger.info("Wait for the object to appear");

			Thread.sleep(500);
			System.out.println("Into waitFor() :: Driver Is:" + driver);
			System.out.println("Into waitFor() :: Object Reference Is:" + objectref);

			System.out.println("...................wait Started........");
			
			if(methodType.equalsIgnoreCase("xpath")) {
				webElement = driver.findElements(By.xpath(objectref));
			}else if(methodType.equalsIgnoreCase("id")) {
				webElement = driver.findElements(By.id(objectref));
			}

			if (webElement.size() > 0) {

				logger.info("Popup is showing:  ... " + inputparam);

			} else

				logger.error("Popup is not showing");

		} catch (Exception e) {
			logger.error("Popup is not showing --- " + e.getMessage());

		}

	}
	

	public void closeBrowser(WebDriver driver) throws Throwable {
		try {

			Thread.sleep(3000);

			System.out.println("Into closeBrowser() :: Driver Is:" + driver);
			
			System.out.println("...................closeBrowser Started........");

			logger.info("Closing the browser");
			  if (driver == null) {
			        return;
			    }
			  driver.quit();
			  driver = null;

			System.out.println("...................closeBrowser Completed........");

		} catch (Exception e) {
			logger.error("Not able to Close the Browser --- " + e.getMessage());

		}
	}

	public void selectDropDown(String objectref, WebDriver driver, String inputparam, String selectionType, String methodType)
			throws Throwable

	{

		try {
			logger.info("Selecting the dropdown text " + objectref);

			Thread.sleep(500);
			System.out.println("Into dropDown() :: Driver Is:" + driver);
			System.out.println("Into dropDown() :: Object Reference Is:" + objectref);
			System.out.println("...................Dropdown Event Started........");

			if(methodType.equalsIgnoreCase("xpath")) {
				dropdown = new Select(driver.findElement(By.xpath(objectref)));
			}else if(methodType.equalsIgnoreCase("id")) {
				dropdown = new Select(driver.findElement(By.id(objectref)));
			}
			
			if (selectionType.equalsIgnoreCase("byvisibletext")) {
				dropdown.selectByVisibleText(inputparam);
			} else if (selectionType.equalsIgnoreCase("byindex")) {
				dropdown.selectByIndex(Integer.parseInt(inputparam));
			} else if (selectionType.equalsIgnoreCase("byindex")) {
				dropdown.selectByValue(inputparam);
			} else {

			}
			System.out.println("...............Dropdown Event Completed........");
			Thread.sleep(3000);

		} catch (Exception e) {
			logger.error("Not able to Select value from the dropdown --- " + e.getMessage());

		}
	}

	public void pressEnter(String objectref, WebDriver driver) throws Throwable {
		try {
			logger.info("Pressing the Enter key ");

			Thread.sleep(500);
			System.out.println("Into copyPasteOTP() :: Driver Is:" + driver);
			System.out.println("Into copyPasteOTP() :: Object Reference Is:" + objectref);

			System.out.println("...................arrowdown Event Started........");
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(3000);

			System.out.println("...................arrowdown Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to press Enter Key --- " + e.getMessage());

		}
	}

	public void mouseHover(String objectref, WebDriver driver, String methodType) throws Throwable {
		try {
			logger.info("Pressing the Enter key ");

			Thread.sleep(500);
			System.out.println("Into copyPasteOTP() :: Driver Is:" + driver);
			System.out.println("Into copyPasteOTP() :: Object Reference Is:" + objectref);

			System.out.println("...................mouseHover Event Started........");
			
			if(methodType.equalsIgnoreCase("xpath")) {
				element = driver.findElement(By.xpath(objectref));
			}else if(methodType.equalsIgnoreCase("id")) {
				element = driver.findElement(By.id(objectref));
			}

			Actions action = new Actions(driver);

			action.moveToElement(element).click().build().perform();

			Thread.sleep(3000);

			System.out.println("...................mouseHover Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to press Enter Key --- " + e.getMessage());

		}
	}

	public void moveToElement(String objectref, WebDriver driver, String methodType) throws Throwable {
		try {
			logger.info("Pressing the Enter key ");

			Thread.sleep(500);
			System.out.println("Into copyPasteOTP() :: Driver Is:" + driver);
			System.out.println("Into copyPasteOTP() :: Object Reference Is:" + objectref);

			System.out.println("...................moveToElement Event Started........");

			if(methodType.equalsIgnoreCase("xpath")) {
				element = driver.findElement(By.xpath(objectref));
			}else if(methodType.equalsIgnoreCase("id")) {
				element = driver.findElement(By.id(objectref));
			}

			Actions action = new Actions(driver);

			action.moveToElement(element);

			Thread.sleep(3000);

			System.out.println("...................moveToElement Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to press Enter Key --- " + e.getMessage());

		}
	}

	public void arrowdown(String objectref, WebDriver driver) throws Throwable

	{
		try {
			logger.info("Entering the text in " + objectref);

			Thread.sleep(500);
			System.out.println("Into copyPasteOTP() :: Driver Is:" + driver);
			System.out.println("Into copyPasteOTP() :: Object Reference Is:" + objectref);

			System.out.println("...................arrowdown Event Started........");

			driver.findElement(By.xpath((objectref))).click();

			Robot robot = new Robot();

			for (int i = 1; i <= 2; i++) {

				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
			}

			Thread.sleep(5000);

			System.out.println("...............arrowdown Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to Input --- " + e.getMessage());

		}
	}

	public void switchWindow(String objectref, WebDriver driver) throws Throwable {
		try {
			logger.info("Switching window event started----  ");

			Thread.sleep(500);
			System.out.println("Into switchWindow() :: Driver Is:" + driver);
			System.out.println("Into switchWindow() :: Object Reference Is:" + objectref);

			System.out.println("...................switchWindow Event Started........");
			
			logger.info("Current window is " + driver.getTitle());

			System.out.println("The current window is " + driver.getTitle());
			Set<String> allWindows = driver.getWindowHandles();
			for (String winHnd : allWindows) {
				// switching to window
				logger.info("Switching to " + driver.getTitle() + "Window");
				driver.switchTo().window(winHnd);
				System.out.println("Switching to " + driver.getTitle() + "Window");
			}

			System.out.println("...................switchWindow Event Completed........");

		} catch (Exception e) {
			logger.error("unable to switch window --- " + e.getMessage());

		}
	}
	
	public void validateText(String objectref, WebDriver driver, String inputparam) throws Throwable

	{
		logger.info("validating the text :  " + inputparam);
		Thread.sleep(500);
		System.out.println("Into validateText() :: Driver Is:" + driver);
		System.out.println("Into validateText() :: Object Reference Is:" + objectref);

		System.out.println(".............validateText started........");

		String bodyText = driver.findElement(By.tagName("body")).getText();
		System.out.println(bodyText);

		if (driver.getPageSource().contains(inputparam) || bodyText.contains(inputparam)) {

			logger.info(inputparam + "" + "Text found");

		} else {
			logger.error("Not able to Validate Text --- ");

		}

		System.out.println(".............validateText completed........");

	}

	public void ListValidate(String objectref, WebDriver driver, String inputparam, String methodType) throws Throwable

	{
		try {
			logger.info("Listing all elements " + objectref);
			Thread.sleep(500);
			System.out.println("Into ListValidate() :: Driver Is:" + driver);
			System.out.println("Into ListValidate() :: Object Reference Is:" + objectref);
			System.out.println("...................ListValidate Event Started........");
			if(methodType.equalsIgnoreCase("xpath")) {
				webElement = driver.findElements(By.xpath(objectref));
			}else if(methodType.equalsIgnoreCase("id")) {
				webElement = driver.findElements(By.id(objectref));
			}

			// System.out.println("The size of the List is:"+e.size());
			System.out.println("The List is:" + webElement);

			Thread.sleep(3000);
			ArrayList<String> s = new ArrayList<String>();

			for (WebElement webElements : webElement) {

				System.out.println("The Weblement Is:" + webElements.getText());

				s.add(webElements.getText());

				System.out.println("The Arraylist Is:" + s);

				if (s.equals(s)) {

					System.out.println("Into If....");

					logger.info(inputparam + "" + "Text found");

				} else {
					System.out.println("Into Else....");

					logger.error("Not able to Validate Text --- ");

				}
			}
			System.out.println("...................ListValidate Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to find the list of elements --- " + e.getMessage());

		}
	}

	public void jsClickOnLinkText(String objectref, WebDriver driver, String inputparam) throws Throwable

	{
		try {
			logger.info("Locating Link Text ");
			Thread.sleep(500);
			System.out.println("Into SpecialclickLinktext() :: Driver Is:" + driver);
			System.out.println("Into SpecialclickLinktext() :: Object Reference Is:" + objectref);

			System.out.println(".............Locating Link Text Started........");

			element = driver.findElement(By.partialLinkText(inputparam));

			System.out.println(".............Locating Link Text completed........");

			if (element.isEnabled() && element.isDisplayed()) {
				System.out.println("Clicking on Link Text with using java script click");
				System.out.println(element.getText());
				logger.info("clicking on Link Text ");

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} else {
				System.out.println("Unable to click on element");
			}
			Thread.sleep(5000);

			System.out.println(".............Clicking on Link Text completed........");

		} catch (Exception e) {
			logger.error("Not able to find the link text --- " + e.getMessage());

		}
	}

	public void clickLinktext(String objectref, WebDriver driver, String inputparam) throws Throwable {
		try {
			logger.info("Locating Link Text ");
			Thread.sleep(500);
			System.out.println("Into clickLinktext() :: Driver Is:" + driver);
			System.out.println("Into clickLinktext() :: Object Reference Is:" + objectref);

			System.out.println(".............Locating Link Text Started........");

			// WebElement E=driver.findElement(By.partialLinkText(data));
			element = driver.findElement(By.linkText(inputparam));

			System.out.println(".............Locating Link Text completed........");

			System.out.println(".............Clicking on Link Text started........");
			System.out.println(element.getText());
			logger.info("clicking on Link Text ");
			element.click();
			System.out.println(".............Clicking on Link Text completed........");

		} catch (Exception e) {
			logger.error("Not able to find the link text --- " + e.getMessage());

		}
	}

	public void clickLink(String objectref, WebDriver driver, String inputparam) throws Throwable {
		try {
			logger.info("Listing all links ");

			Thread.sleep(500);
			System.out.println("Into ClickLink() :: Driver Is:" + driver);
			System.out.println("Into ClickLink() :: Object Reference Is:" + objectref);
			System.out.println("Into ClickLink() :: Input Parameter Is:" + inputparam);

			System.out.println("...................ClickFirstlink Event Started........");

			webElement = driver.findElements(By.xpath(objectref));
			System.out.println(webElement.size());
			Thread.sleep(3000);
			for (WebElement webElements : webElement) {

				System.out.println(webElements.getText());
				if (webElements.getText().contains(inputparam)) {

					logger.info("Clicking on the link ");

					webElements.click();

				}

				else {
					logger.error("Not able to click link text --- ");

				}

			}
			System.out.println("...................ClickFirstlink Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to find the list of elements --- " + e.getMessage());

		}
	}

	public void ClickFirstlink(String objectref, WebDriver driver) throws Throwable {
		try {
			logger.info("Listing all links ");

			Thread.sleep(500);
			System.out.println("Into ClickFirstlink() :: Driver Is:" + driver);
			System.out.println("Into ClickFirstlink() :: Object Reference Is:" + objectref);
			System.out.println("...................ClickFirstlink Event Started........");

			webElement = driver.findElements(By.xpath(objectref));
			System.out.println(webElement.size());
			Thread.sleep(3000);

			System.out.println(webElement.get(0));

			logger.info("Clicking on the first link ");

			webElement.get(0).click();

			System.out.println("...................ClickFirstlink Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to find the list of elements --- " + e.getMessage());

		}
	}

	public void ListClick(String objectref, WebDriver driver) throws Throwable

	{
		try {
			logger.info("Listing all elements " + objectref);
			Thread.sleep(500);
			System.out.println("Into ListClick() :: Driver Is:" + driver);
			System.out.println("Into ListClick() :: Object Reference Is:" + objectref);

			System.out.println("...................ListClick Event Started........");

			webElement = driver.findElements(By.xpath(objectref));
			System.out.println(webElement.size());
			Thread.sleep(3000);
			ArrayList<String> s = new ArrayList<String>();

			for (WebElement webElements : webElement) {

				System.out.println(webElements.getText());
				s.add(webElements.getText());

				Thread.sleep(3000);
				if (webElements.isEnabled() && webElements.isDisplayed()) {
					// clicking on the element

					logger.info("Clicking on the element ");

					webElements.click();

				}
			}

			System.out.println("...................ListClick Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to find the list of elements --- " + e.getMessage());

		}
	}

	// ALERT HANDLE

	public void popupHandle(String objectref, WebDriver driver, String inputparam) throws Throwable

	{
		try {
			logger.info("handling pop up  ");

			Thread.sleep(500);
			System.out.println("Into popupHandle() :: Driver Is:" + driver);
			System.out.println("Into popupHandle() :: Object Reference Is:" + objectref);
			System.out.println("Into popupHandle() :: Input Parameter Is:" + inputparam);

			System.out.println(".............popupHandle Started........");

			Alert alert = driver.switchTo().alert();

			// getting text of the pop up message
			String str = alert.getText();
			logger.info("The message is...  " + str);

			if (str.contains(inputparam)) {

				logger.info("This is the correct pop up message:  ... ");

			}

			else {
				logger.error("This is not the desired message" + "The message sould be :" + inputparam);

			}

			// clicking on OK button
			alert.accept();
			driver.switchTo().defaultContent();

			System.out.println(".............popupHandle Completed........");

			Thread.sleep(3000);
			// captureScreenShot(driver);

		} catch (Exception e) {
			logger.error("Not able to handle pop up --- " + e.getMessage());

		}
	}

	public void alrtClickAccept(String objectref, WebDriver driver, String inputparam) throws Throwable

	{
		try {
			logger.info("Clicking on Webelement " + objectref);
			Thread.sleep(500);
			System.out.println("Into alrtClick() :: Driver Is:" + driver);
			System.out.println("Into alrtClick() :: Object Reference Is:" + objectref);
			System.out.println("Into alrtClick() :: Input Parameter Is:" + inputparam);

			System.out.println("...................Alert Click Event Started........");

			// click the button
			//driver.findElement(By.xpath(objectref)).click();

			// handling windows popup
			Thread.sleep(5000);
			Alert alert = driver.switchTo().alert();

			// getting text of the pop up message
			String str = alert.getText();
			System.out.println("The message is...  " + str);
			logger.info("The message is...  " + str);

			if (str.contains(inputparam)) {

				System.out.println("Into str.contains(inputparam)");
				logger.info("This is the correct pop up message:  ... ");

			}

			else {
				System.out.println("Not into str.contains(inputparam)");
				logger.error("This is not the desired message" + "The message sould be :" + inputparam);

			}

			System.out.println("Now trying to click on the alert popup");
			// clicking on OK button
			alert.accept();

			System.out.println("After trying to click on the alert popup");

			driver.switchTo().defaultContent();

			System.out.println("Switching to default content");

			System.out.println("...............Alert Click Event Completed........");

			Thread.sleep(10000);
			// captureScreenShot(driver);

		} catch (Exception e) {
			System.out.println("Not able to click the alert message --- " + e.getMessage());
			logger.error("Not able to click the alert message --- " + e.getMessage());

		}
	}

	public void doubleAlrtClickAccept(String objectref, WebDriver driver, String inputparam) throws Throwable

	{
		try {
			logger.info("Clicking on Webelement " + objectref);
			Thread.sleep(500);
			System.out.println("Into alrtClick() :: Driver Is:" + driver);
			System.out.println("Into alrtClick() :: Object Reference Is:" + objectref);
			System.out.println("Into alrtClick() :: Input Parameter Is:" + inputparam);

			System.out.println("...................Alert Click Event Started........");

			// click the button
			//driver.findElement(By.xpath(objectref)).click();

			// handling windows popup
			Thread.sleep(5000);
			Alert alert = driver.switchTo().alert();

			// getting text of the pop up message
			String str = alert.getText();
			System.out.println("The message is...  " + str);
			logger.info("The message is...  " + str);
			System.out.println("Now trying to click on the alert popup");

			// clicking on OK button
			alert.accept();
			Thread.sleep(10000);
			alert.accept();

			System.out.println("After trying to click on the alert popup");

			driver.switchTo().defaultContent();

			System.out.println("Switching to default content");

			System.out.println("...............Alert Click Event Completed........");

			Thread.sleep(10000);
			// captureScreenShot(driver);

		} catch (Exception e) {
			System.out.println("Not able to click the alert message --- " + e.getMessage());
			logger.error("Not able to click the alert message --- " + e.getMessage());

		}
	}

	public void alrtClickDismiss(String objectref, WebDriver driver, String inputparam) throws Throwable

	{
		try {
			logger.info("Clicking on Webelement " + objectref);
			Thread.sleep(500);
			System.out.println("Into alrtClick() :: Driver Is:" + driver);
			System.out.println("Into alrtClick() :: Object Reference Is:" + objectref);
			System.out.println("Into alrtClick() :: Input Parameter Is:" + inputparam);

			System.out.println("...................Alert Click Event Started........");

			// click the button
//			/driver.findElement(By.xpath(objectref)).click();

			// handling windows popup
			Thread.sleep(5000);
			Alert alert = driver.switchTo().alert();

			// getting text of the pop up message
			String str = alert.getText();
			System.out.println("The message is...  " + str);
			logger.info("The message is...  " + str);

			if (str.contains(inputparam)) {

				System.out.println("Into str.contains(inputparam)");
				logger.info("This is the correct pop up message:  ... ");

			}

			else {
				System.out.println("Not into str.contains(inputparam)");
				logger.error("This is not the desired message" + "The message sould be :" + inputparam);

			}

			System.out.println("Now trying to click on the alert popup");
			// clicking on OK button
			alert.dismiss();

			System.out.println("After trying to click on the alert popup");

			driver.switchTo().defaultContent();

			System.out.println("Switching to default content");

			System.out.println("...............Alert Click Event Completed........");

			Thread.sleep(10000);
			//captureScreenShot(driver);

		} catch (Exception e) {
			System.out.println("Not able to click the alert message --- " + e.getMessage());
			logger.error("Not able to click the alert message --- " + e.getMessage());

		}
	}

	public void autoITWindowsHandle(String objectref, WebDriver driver) throws Throwable {
		
		Properties properties=obj.getProperty(); 
		
		try {
			logger.info("Clicking on Webelement " + objectref);

			Thread.sleep(500);
			System.out.println("Into autoITWindowsHandle() :: Driver Is:" + driver);
			System.out.println("Into autoITWindowsHandle() :: Object Reference Is:" + objectref);

			System.out.println("...................Alert Click Event Started........");

			Thread.sleep(3000);
			// to call AutoIT exe
			Runtime.getRuntime().exec(properties.getProperty("AUTOID_PATH"));

			System.out.println("...............Alert Click Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to click the alert message --- " + e.getMessage());

		}
	}

	public void findObject(String objectref, WebDriver driver) throws Throwable

	{

		try {

			logger.info("Locating object " + objectref);
			Thread.sleep(500);
			System.out.println("Into findObject() :: Driver Is:" + driver);
			System.out.println("Into findObject() :: Object Reference Is:" + objectref);

			System.out.println("...................findObject Event Started........");

			WebElement E = driver.findElement(By.xpath(objectref));

			if (E.isDisplayed()) {
				logger.info("Object found");

			}

			System.out.println("...................findObject Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to Locate object --- " + e.getMessage());

		}

	}
	   
	   
	public boolean validateListItems(String objectref, WebDriver driver, String methodType, List<String> expectedOptions) throws Throwable

	{

		Boolean isMatched = false;
		
		List<String> options = new ArrayList<String>();
		try {

			logger.info("Locating object " + objectref);
			Thread.sleep(500);
			System.out.println("Into findObject() :: Driver Is:" + driver);
			System.out.println("Into findObject() :: Object Reference Is:" + objectref);
			System.out.println("Into findObject() :: methodType Reference Is:" + methodType);
			System.out.println("Into findObject() :: expectedOptions Reference Is:" + expectedOptions);

			System.out.println("...................validateListItems Event Started........");

			if(methodType.equalsIgnoreCase("xpath")) {
				options = getAllOptions(By.xpath(objectref), driver);
			}else if(methodType.equalsIgnoreCase("id")) {
				options = getAllOptions(By.id(objectref), driver);
			}
			
			if(options.equals(expectedOptions)) {
				isMatched = true;
				logger.info("isMatched " + isMatched);
			}else {
				isMatched = false;
				logger.info("isMatched " + isMatched);
			}
			System.out.println("...................validateListItems Event Completed........");

		} catch (Exception e) {
			logger.error("Not able to Locate object --- " + e.getMessage());

		}
		
		return isMatched;

	}
	
	public List<String> getAllOptions(By dropdown, WebDriver driver){
		 List<String> options = new ArrayList<String>();
		    for (WebElement option : new Select(driver.findElement(dropdown)).getOptions()) {
		        String optionTxt = option.getText();
		        logger.info("Options are : " + optionTxt);
		        if (option.getAttribute("value") != "") options.add(option.getText());
		    }
		    return options;
	}
	/*
	 * public static void main(String args[]) {
	 * System.out.println(getCurrentTime()); }
	 */

}

