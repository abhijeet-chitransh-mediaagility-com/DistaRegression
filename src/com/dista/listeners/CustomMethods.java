package com.dista.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.dista.test.automation.TestLaunchApplication;
import com.dista.utilities.GenUtilities;

public class CustomMethods extends TestLaunchApplication implements ITestListener {
String filePath = "screenshots/Failures";
	
	//ExtentReport er = new ExtentReport();

	@Override
	public void onFinish(ITestContext arg0) {
		
		
		
		//er.extentReports();
		
		
	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
	    	
	    	System.out.println("***** Error "+result.getName()+" test has failed *****");
	    	String methodName=result.getName().toString().trim();
	    	
	    	try {
			String pathScreenshot=	GenUtilities.captureSnap(driver, methodName);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	
	

	@Override
	public void onTestSkipped(ITestResult result) {
    	System.out.println("***** Error "+result.getName()+" test has skipped *****");
    	String methodName=result.getName().toString().trim();
    	
    	try {
			GenUtilities.captureScreenshot(methodName);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

}
