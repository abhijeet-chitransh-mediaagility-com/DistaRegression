package com.dista.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

import com.dista.methods.DynamicXml;
import com.dista.methods.ResultRecording;

public class Main{

	public static void main(String[] args) throws IOException, InterruptedException {

				
				
				
		DynamicXml poiExample = new DynamicXml ();
	    poiExample.convertFile("Support/AutomationData.xlsx", "XMLFile/testng.xml");
		
		// Create object of TestNG Class
		TestNG runner=new TestNG();

		// Create a list of String 
		List<String> suitefiles=new ArrayList<String>();

		// Add xml file which you have to execute
		suitefiles.add("XMLFile/testng.xml");

		// now set xml file for execution
		runner.setTestSuites(suitefiles);

		// finally execute the runner using run method
		runner.run();
        System.err.println("Return code : " + runner.getStatus());

		
		
		
		
		ResultRecording rr= new ResultRecording();
		rr.writeMail("Report","PFA");
		
		
		
		}
}