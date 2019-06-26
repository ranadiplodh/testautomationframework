package com.testautomation.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;


public class ITestListenerImpl extends ExtentReportListener implements ITestListener
{
	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
    	/*extendReportListener.test = extendReportListener.extent.createTest(result.getTestName());
    	extendReportListener.test = extendReportListener.test.createNode(result.getMethod().getMethodName());
    	
    	extendReportListener.test.log(Status.INFO, result.getMethod().getMethodName() + "test is started");*/
	}

	public void onTestSuccess(ITestResult result) {
//		PropertiesFileReader obj= new PropertiesFileReader();	
//		TestDataHandler testdata=new TestDataHandler();
//		Map<String,String> testData=testdata.getTestDataInMap();
//		
//		Properties properties;
//		try {
//			properties = obj.getProperty();
//			ExcelHandler.UpdateTestResultsToExcel( properties.getProperty("testdatafilepath"), 
//					properties.getProperty("sheetname"),"PASS",testData.get("TestCaseId"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  		
//		
		
		System.out.println("PASS");
		
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("FAIL");
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("SKIP");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		System.out.println("Execution started on UAT env...");
		extent= setUp();
	}

	public void onFinish(ITestContext context) {
		System.out.println("Execution completed on UAT env...");
		extent.flush();		
		System.out.println("Generated Report. . .");	
		
	}
}
