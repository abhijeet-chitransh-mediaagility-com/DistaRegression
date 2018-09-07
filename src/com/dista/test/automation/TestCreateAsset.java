package com.dista.test.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dista.utilities.GenUtilities;

public class TestCreateAsset extends TestLaunchApplication{
	
    private ITestContext context;  // creating a ITestContext variable

	
/*	@BeforeClass
	public void begin(ITestContext iTestContext) {
		
		System.out.println(" Before test begin");
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).build().perform();
		
        this.context = setContext(iTestContext, driver);  // setting the driver into context
		GenUtilities.waitTillPageLoad(driver);	
		
	}*/ 
	
    
    @BeforeTest
    public void begin(ITestContext iTestContext) {
		try{
			Add_Log.info("Execution started for CreateAsset class.");
			Thread.sleep(10000);
			
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).build().perform();
			 
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
		            public Boolean apply(WebDriver driver) {
		                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
		            }
		        };
		    WebDriverWait wait = new WebDriverWait( driver, 30);
		
		    wait.until(pageLoadCondition);
		    
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
    
	
	
	
	
	
	@Test
	public void createAssetOne() throws InterruptedException {
		Thread.sleep(3000L);

		driver.findElement(By.xpath("//a[(@data-toggle='offcanvas')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Jobs Management')]")).click();
		driver.findElement(By.xpath("//div[text()='Create Job']")).click();                 
		driver.findElement(By.xpath("//a[@id='6466325325021184_anchor']")).click();
		driver.findElement(By.xpath("//button[(@ng-click='accessSelected()')]")).click();                        
		driver.findElement(By.id("searchAccess")).click();
		System.out.println("Asset one created");

	}
	
	@Test
	public void createAssetTwo() {
		
		System.out.println("Asset two created");
	}
}
