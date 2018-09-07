package com.dista.extentreports;
//https://github.com/swtestacademy/extent-reports-version-3-example/
import com.relevantcodes.extentreports.ExtentReports;

//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager {

  private static ExtentReports extent;

  public synchronized static ExtentReports getReporter(){
      if(extent == null){
          //Set HTML reporting file location
          String workingDir = System.getProperty("user.dir");
          extent = new ExtentReports(workingDir+"/TestReport/ExtentReportResults.html", true);
      }
      return extent;
  }
}