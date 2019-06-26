package com.testautomation.TestRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.testautomation.Listeners.ExtentReportListener;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.*;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "./features/"
        ,glue = {"com.testautomation.StepDef"}
        ,tags = {"@DigitalPMO"}
        ) 
		
public class TestRunner {
    private TestNGCucumberRunner testNGCucumberRunner;   
    
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {    	
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(dataProvider = "features")    
    public void feature(PickleEventWrapper eventwrapper,CucumberFeatureWrapper cucumberFeature) throws Throwable {
    	testNGCucumberRunner.runScenario(eventwrapper.getPickleEvent());
    	
    }
    
    @DataProvider//(parallel=true)
    public Object[][] features() {           	
    	 return testNGCucumberRunner.provideScenarios();
    }
    
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {    	
        testNGCucumberRunner.finish();        
    }
 
}